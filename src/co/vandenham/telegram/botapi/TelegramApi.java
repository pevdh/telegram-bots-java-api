package co.vandenham.telegram.botapi;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by pieter on 24-7-15.
 */
public final class TelegramApi {

    //Non-instantiable nor subclassable class.
    private TelegramApi() {}

    public static final String API_URL =  "https://api.telegram.org/bot%s/%s";

    public static String makeGetRequest(String token, String method) {
        try {
            HttpsURLConnection connection = buildConnection(token, method);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            return readAll(connection.getInputStream());
        } catch (IOException exception) {
            throw new ApiException(method, exception);
        }
    }

    public static String makePostRequest(String token, String method, Map<String, String> arguments) {
        try {
            String query = createQueryString(arguments);

            HttpsURLConnection connection = buildConnection(token, method);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-length", String.valueOf(query.length()));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");

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

    public static String createQueryString(Map<String, String> arguments) throws UnsupportedEncodingException {
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

    private static HttpsURLConnection buildConnection(String token, String methodName) {
        try {
            return (HttpsURLConnection) new URL(String.format(API_URL, token, methodName)).openConnection();
        } catch (IOException e) {
            throw new ApiException(methodName, e);
        }
    }

}
