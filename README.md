# laser-tag

## How to run the project (Maven-based project)
### 1. A simple way is using ides
You can use ides such as IntelliJ, Eclipse

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
### Github names of team 3
| Real Name | Github Name |
| --- | ----------- |
| Norah Rogers | nojrogers |
| Blake Tilton | blaketilton |
| Craig Yetter | crayetter |
| Jisu Kim | mongtee |
