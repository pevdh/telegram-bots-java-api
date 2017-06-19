#!/bin/bash

set -eu

# Escape code
esc=$(echo -en "\033")

# Set colors
info="${esc}[0;33m"
normal=$(echo -en "${esc}[m\017")

WORKDIR=$(pwd)

PROFILE=${1:-dev}
MAVEN_BUILD_PREP=${MAVEN_BUILD_PREP:-"clean install"}
GRADLE_BUILD_PREP=${GRADLE_BUILD_PREP:-"clean install"}
DO_NOT_UPLOAD_ARTIFACT=" "

displayGeneralInfo()
{
    echo ""
    echo "${info}*************************************************************************************************************************************************"
    echo "Please ensure the below are in place:"
    echo " - you have Java version 1.8.0 or higher installed"
    echo " - you are setup on JFrog Artifactory (http://jfrog.com), and downloaded the generated setting.xml and placed it into ~/.m2 (see JFrog/Artifactory docs for instructions)"
    echo "*************************************************************************************************************************************************"
    echo "Any build failures are a result of the above not in place"
    echo "*************************************************************************************************************************************************${normal}"
    echo ""
}

doJavaVersionCheck()
{
    java -version 2>&1 | \
    grep -q '"1.[8-9].[0-9]' ||   \
    (echo "**** Fix your Java version, at least 1.8.0 is required! ****" && \
     echo "**** Here is your current Java version info ****" && \
    java -version && exit 1)

}

buildTelegramBot()
{
   TELEGRAM_BOT_REPO=telegram-bots-java-api
   echo ""
   echo "${info}Building the '$TELEGRAM_BOT_REPO' jar file${normal}"
   ./gradlew :api:jar
   ./gradlew :EchoBot:jar
   ./gradlew :GMTBot:jar
   echo ""
   echo "${info}Installing the '$TELEGRAM_BOT_REPO' jar into the local repository${normal}"
   ./gradlew $GRADLE_BUILD_PREP
   echo ""
}

displayGeneralInfo
doJavaVersionCheck
buildTelegramBot
