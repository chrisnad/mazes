#!/usr/bin/env zsh

JAR_SIZE=$(wc -c ../target/mazes-1.0.jar | awk '{printf "%d", $1/1000000}')
NATIVE_EXE_SIZE=$(wc -c ../mazes | awk '{printf "%d", $1/1000000}')
JDK_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:jdk | numfmt --to=si | sed 's/.$//')
JRE_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:jre | numfmt --to=si | sed 's/.$//')
CUSTOM_JRE_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:custom-jre | numfmt --to=si | sed 's/.$//')
JIB_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:jib | numfmt --to=si | sed 's/.$//')
CNB_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:cnb | numfmt --to=si | sed 's/.$//')
PACK_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:pack | numfmt --to=si | sed 's/.$//')
NATIVE_CNB_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:cnb-native | numfmt --to=si | sed 's/.$//')
NATIVE_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:native | numfmt --to=si | sed 's/.$//')
NATIVE_UPX_IMG_SIZE=$(docker inspect -f "{{ .Size }}" mazes:native-x | numfmt --to=si | sed 's/.$//')

# Chart of exe and jar sizes
echo "
    .JAR       $JAR_SIZE
    Native-exe $NATIVE_EXE_SIZE" |
  termgraph --title "Exe & jar Size" --width 7 --color red --suffix " MB"

# Chart of the image sizes
echo "
    JDK        $JDK_IMG_SIZE
    JRE        $JRE_IMG_SIZE
    Jlink-JRE  $CUSTOM_JRE_IMG_SIZE
    JIB        $JIB_IMG_SIZE
    CNB        $CNB_IMG_SIZE
    PACK       $PACK_IMG_SIZE
    Native-CNB $NATIVE_CNB_IMG_SIZE
    Native     $NATIVE_IMG_SIZE
    Native-X   $NATIVE_UPX_IMG_SIZE" |
  termgraph --title "Container Size" --width 60 --color green --suffix " MB"
