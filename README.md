# Mazes
A Maze Generator


## CNB
prerequisites:
* `docker`

build with Maven (build time 18s): 
```shell
./mvnw clean spring-boot:build-image \
      -Dspring-boot.build-image.imageName=mazes:cnb
```

run as webapp: 
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:cnb
```

run as cli:
```shell
docker run -it -ePORT=8080 -eMAZES_CLI=yes -p8080:8080 mazes:cnb
```

## Native CNB
prerequisites: 
* `docker (started with 4 CPU & 8Gb RAM)`
* `java 22.3.1.r17-grl`

build with Maven (build time 06:40 min):
```shell
./mvnw clean spring-boot:build-image \
      -Pnative \
      -Dspring-boot.build-image.imageName=mazes:cnb-native
```

run the app:
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:cnb-native
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

## Bonus: Native Executable
set environment variable: 
```shell
export MAZES_CLI=yes
```

build exe:
```shell
./mvnw clean package -Pnative
```

run cli:
```shell
target/mazes

target/mazes sw 6 9
```
