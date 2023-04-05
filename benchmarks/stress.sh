#!/usr/bin/env zsh

# Create files for storing the stats
mkdir -p stats
cd stats || exit
rm -f JDK.txt      JDK_ALL.txt \
      JRE.txt      JRE_ALL.txt \
      JLINK.txt    JLINK_ALL.txt \
      JIB.txt      JIB_ALL.txt \
      CNB.txt      CNB_ALL.txt \
      PACK.txt     PACK_ALL.txt \
      NI.txt       NI_ALL.txt   \
      NI_CNB.txt   NI_CNB_ALL.txt \
      NI_X.txt     NI_X_ALL.txt
      
touch JDK.txt      JDK_ALL.txt \
      JRE.txt      JRE_ALL.txt \
      JLINK.txt    JLINK_ALL.txt \
      JIB.txt      JIB_ALL.txt \
      CNB.txt      CNB_ALL.txt \
      PACK.txt     PACK_ALL.txt \
      NI.txt       NI_ALL.txt   \
      NI_CNB.txt   NI_CNB_ALL.txt \
      NI_X.txt     NI_X_ALL.txt

echo -ne "Stress Testing..."

# Requests/sec:
hey -z 15s -c 4 http://localhost:8081/ | tee JDK_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > JDK.txt &
hey -z 15s -c 4 http://localhost:8082/ | tee JRE_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > JRE.txt &
hey -z 15s -c 4 http://localhost:8083/ | tee JLINK_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > JLINK.txt &
hey -z 15s -c 4 http://localhost:8084/ | tee JIB_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > JIB.txt &
hey -z 15s -c 4 http://localhost:8085/ | tee CNB_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > CNB.txt &
hey -z 15s -c 4 http://localhost:8086/ | tee PACK_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > PACK.txt &
hey -z 15s -c 4 http://localhost:8087/ | tee NI_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > NI.txt &
hey -z 15s -c 4 http://localhost:8088/ | tee NI_CNB_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > NI_CNB.txt &
hey -z 15s -c 4 http://localhost:8089/ | tee NI_X_ALL.txt | grep --color=auto -Eo '95% in [0-9]+.[0-9]+ secs' > NI_X.txt &

# Progress bar
for p in {1..16}; do
  echo -ne "#";
  sleep 1
done
echo -ne " DONE"

JDK_LAT=`cat JDK.txt | sed 's/95% in //' | sed 's/ secs//'` && JDK_LAT=$((JDK_LAT * 1000))
JRE_LAT=`cat JRE.txt | sed 's/95% in //' | sed 's/ secs//'` && JRE_LAT=$((JRE_LAT * 1000))
JLINK_LAT=`cat JLINK.txt | sed 's/95% in //' | sed 's/ secs//'` && JLINK_LAT=$((JLINK_LAT * 1000))
JIB_LAT=`cat JIB.txt | sed 's/95% in //' | sed 's/ secs//'   ` && JIB_LAT=$((JIB_LAT * 1000))
CNB_LAT=`cat CNB.txt | sed 's/95% in //' | sed 's/ secs//'` && CNB_LAT=$((CNB_LAT * 1000))
PACK_LAT=`cat PACK.txt | sed 's/95% in //' | sed 's/ secs//'` && PACK_LAT=$((PACK_LAT * 1000))
NI_LAT=`cat NI.txt | sed 's/95% in //' | sed 's/ secs//'` && NI_LAT=$((NI_LAT * 1000))
NI_CNB_LAT=`cat NI_CNB.txt | sed 's/95% in //' | sed 's/ secs//'` && NI_CNB_LAT=$((NI_CNB_LAT * 1000))
NI_X_LAT=`cat NI_X.txt | sed 's/95% in //' | sed 's/ secs//'` && NI_X_LAT=$((NI_X_LAT * 1000))


JDK_REQS=`cat JDK_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
JRE_REQS=`cat JRE_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
JLINK_REQS=`cat JLINK_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
JIB_REQS=`cat JIB_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
CNB_REQS=`cat CNB_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
PACK_REQS=`cat PACK_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
NI_REQS=`cat NI_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
NI_CNB_REQS=`cat NI_CNB_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`
NI_X_REQS=`cat NI_X_ALL.txt | grep -Eo '[[:space:]]+Requests/sec:[[:space:]][0-9]+.[0-9]+' | awk '{print $2}'`

echo "JDK           $JDK_LAT
      JRE           $JRE_LAT
      JLINK         $JLINK_LAT
      JIB           $JIB_LAT
      CNB           $CNB_LAT
      PACK          $PACK_LAT
      Native-CNB    $NI_CNB_LAT
      Native        $NI_LAT
      Native-X      $NI_X_LAT" \
      | termgraph --title "Latency of 95% of Requests" --width 60 --color yellow --suffix " ms"

echo "JDK           $JDK_REQS
      JRE           $JRE_REQS
      JLINK         $JLINK_REQS
      JIB           $JIB_REQS
      CNB           $CNB_REQS
      PACK          $PACK_REQS
      Native-CNB    $NI_CNB_REQS
      Native        $NI_REQS
      Native-X      $NI_X_REQS" \
      | termgraph --title "Requests / seconds" --width 60 --color magenta --suffix " req / s"

cd ..
