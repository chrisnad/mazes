# Mazes
Maze Generator


## CNB
prerequisites:
* `docker`

build with Maven (build time 18s): 
```shell
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=mazes:cnb
```

running the app: 
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:cnb
```

## Native CNB
prerequisites: 
* `docker (started with 4 CPU & 8Gb RAM)`

build with Maven (build time 06:40 min):
```shell
./mvnw -Pnative spring-boot:build-image -Dspring-boot.build-image.imageName=mazes:native-cnb
```

running the app:
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:native-cnb
```

## Native CNB from jar

build AOT processed Spring Boot executable jar:
```shell
./mvnw clean package
```

then build with pack:
```shell
pack build --builder paketobuildpacks/builder:tiny \
    --path target/mazes-1.0-exec.jar \
    --env 'BP_NATIVE_IMAGE=true' \
    mazes:native-jar-cnb
```
