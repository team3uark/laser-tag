# laser-tag

## How to run the project (Maven-based project)
### 1. A simple way is using ides
You can use ides such as IntelliJ, Eclipse
  To import a project from github to Eclipse: https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/How-to-import-a-Maven-project-from-GitHub-into-
  Eclipse#:~:text=Open%20Eclipse%20and%20choose%20Import,remote%20repository%20and%20click%20Next
  If there are errors after importing the project: 
  1.right-click on project folder
  2.Select Maven
  3.Select Update Project

### 2. Using maven cli to build jar
If you are on a mac, you can run:
```
brew install maven
```
Or you can install with: https://maven.apache.org/install.html

Then to build the jar and run it, you use:
```
# run this in the root of the project, where the pom.xml file is
mvn clean package
java -cp target/laserTag-1.0-SNAPSHOT.jar Main
```

### How to run Udp Sockets
1. You need to run UdpServer.java
2. Then you can run the application (Main.java) and you can enter equipment ids
3. You will see the equipment ids on the screen of UdpServer.java

-----------------------------
