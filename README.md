# ChainLinksCodingExercise
Chain Link Game
The Links game is a two-player strategy game where the objective is to collect more points than the other player by taking links out of either end of a chain.
The game is played by taking turns to choose a link, with the player who collects the most valuable links winning.

In this project, I have used JavaFx as the front-end technology for building the user interface.
Additionally, the project also integrates with Spring Boot, to make it possible to play the game using API's(not implemented)

# How to run
1. Run JAR
    - **_chmod -x gradlew_** - Make gradlew file as executable
    - **_sudo bash ./gradlew_**  -Execute the file gradlew to build the project- 
    - Point to the correct java version -  Java – version 8 corretto-1.8
       - Article - https://medium.com/@devkosal/switching-java-jdk-versions-on-macos-80bc868e686a
       Example Code
        - **_/usr/libexec/java_home -V_**
        - **_export JAVA_HOME=`/usr/libexec/java_home -v 1.8`_**
    - **_java -jar build/libs/ChainLinks-1.0.2.jar_**

2. Running through IDE
    - Download Zip file from github & extract it
    - Install Intellij
      - IntelliJ IDEA 2022.3.2 (Community Edition)
    - Install Maven - version being used: 7.5.1
    - Open the extracted project in intellij
    - Install JDK - Java – version 8 corretto-1.8
       a.Manually
           install the corretto-8 JDK from the link - https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/downloads-list.html
       b. IntelliJ gives a Prompt to install the same
      Set the SDK in intellij to correto 1.8 (File -> Project Structure -> SDK)
    - Create a new cofiguration 
       Choose Application, 
        Build and run
        -> Java 8 (correto 1.8) -> cp = ChainLinks.main -> main class = com.schonfeld.LinkGameSpring

