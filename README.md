# laser-tag

## Using maven cli to build jar

If you are on a mac, you can run:
```
brew install maven
```
Or you can install with: https://maven.apache.org/install.html

Then to build the jar and run it you use:
```
mvn clean package
java -cp target/laserTag-1.0-SNAPSHOT.jar Main
```