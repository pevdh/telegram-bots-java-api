package co.vandenham.telegram.botapi.requests;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public final class TelegramApi {

    public static final String API_URL = "https://api.telegram.org/bot%s/%s";
    private final String token;

    public TelegramApi(String token) {
        this.token = token;
    }

    private static String createQueryString(Map<String, String> arguments) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : arguments.entrySet()) {
            stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                    .append("&");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    private static String readAll(InputStream input) {
        Scanner scanner = new Scanner(input);
        scanner.useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : null;
    }

    public String makeGetRequest(String method) {
        try {
            HttpsURLConnection connection = buildConnection(method);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            return readAll(connection.getInputStream());
        } catch (IOException exception) {
            throw new ApiException(method, exception);
        }
    }

    public String makePostRequest(String method, Map<String, String> arguments) {
        try {
            String query = createQueryString(arguments);

            HttpsURLConnection connection = buildConnection(method);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-length", String.valueOf(query.length()));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent", "TelegramBots4Java API Agent");

            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(query);
            output.close();

            return readAll(connection.getInputStream());
        } catch (IOException exception) {
            throw new ApiException(method, exception);
        }
    }

    public String makeMultipartRequest(String method, Map<String, String> args, String fieldName, File uploadFile) {
        try {
            MultipartUtility multipartUtility = new MultipartUtility(buildConnection(method), "UTF-8");

            for (Map.Entry<String, String> entry : args.entrySet())
                multipartUtility.addFormField(entry.getKey(), entry.getValue());

            multipartUtility.addFilePart(fieldName, uploadFile);

            return multipartUtility.finish();
        } catch (IOException e) {
            throw new ApiException(method, e);
        }
    }

    private HttpsURLConnection buildConnection(String methodName) {
        try {
            return (HttpsURLConnection) new URL(String.format(API_URL, token, methodName)).openConnection();
        } catch (IOException e) {
            throw new ApiException(methodName, e);
        }
    }

    private static class MultipartUtility {
        private static final String LINE_FEED = "\r\n";
        private final String boundary;
        private HttpsURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         *
         * @param httpConn
         * @param charset
         * @throws IOException
         */
        public MultipartUtility(HttpsURLConnection httpConn, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";
            this.httpConn = httpConn;

            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("User-Agent", "TelegramBots4Java API Agent");
            httpConn.setRequestProperty("Test", "Bonjour");
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         *
         * @param name  field name
         * @param value field value
         */
        public void addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a upload file section to the request
         *
         * @param fieldName  name attribute in <input type="file" name="..." />
         * @param uploadFile a File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, File uploadFile)
                throws IOException {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         *
         * @param name  - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         *
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public String finish() throws IOException {
            List<String> response = new ArrayList<String>();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            outputStream.close();
            return readAll(httpConn.getInputStream());
        }
    }

}
