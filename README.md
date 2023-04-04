# Mazes
Maze Generator

## Native CNB

prerequisites: 
* `Java 17`
* `docker (4 CPU - 8Gb RAM)`

build with Maven: `mvn -Pnative spring-boot:build-image `

running the app: `docker run -it -ePORT=8080 -p8080:8080 mazes:1.0 ` 

## Native CNB from jar

build AOT processed Spring Boot executable jar: `./mvnw clean package`

```shell
pack build --builder paketobuildpacks/builder:tiny \
    --path target/mazes-1.0-exec.jar \
    --env 'BP_NATIVE_IMAGE=true' \
    mazes:native-jar-cnb
```
