# Mazes
Maze Generator

## Native CNB

prerequisites: 
* `Java 17`
* `docker (4 CPU - 8Gb RAM)`

build with Maven: `mvn -Pnative spring-boot:build-image `

running the app: `docker run -it -ePORT=8080 -p8080:8080 mazes:0.0.1-SNAPSHOT ` 

## Native
