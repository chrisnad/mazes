#!/usr/bin/env zsh

# Extract time to start from the logs - convert to ms
PH_JDK=$(docker logs --tail 10 mazes-jdk | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_JRE=$(docker logs --tail 10 mazes-jre | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_JLINK=$(docker logs --tail 10 mazes-custom-jre | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_JIB=$(docker logs --tail 10 mazes-jib | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_CNB=$(docker logs --tail 10 mazes-cnb | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_PACK=$(docker logs --tail 10 mazes-pack | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_NATIVE_CNB=$(docker logs --tail 10 mazes-cnb-native | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_NATIVE=$(docker logs --tail 10 mazes-native | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')
PH_NATIVE_X=$(docker logs --tail 10 mazes-native-x | grep -Eo 'Started .+ in [0-9]+.[0-9]+ sec' | sed 's/[[:space:]]sec$//' | sed 's/.* //')

# Convert to ms
JDK_START=$(( PH_JDK * 1000 ))
JRE_START=$(( PH_JRE * 1000 ))
JLINK_START=$(( PH_JLINK * 1000 ))
JIB_START=$(( PH_JIB * 1000 ))
CNB_START=$(( PH_CNB * 1000 ))
PACK_START=$(( PH_PACK * 1000 ))
NATIVE_CNB_START=$(( PH_NATIVE_CNB * 1000 ))
NATIVE_START=$(( PH_NATIVE * 1000 ))
NATIVE_X_START=$(( PH_NATIVE_X * 1000 ))

# Display as a chart
echo "JDK           $JDK_START
      JRE           $JRE_START
      JLINK         $JLINK_START
      JIB           $JIB_START
      CNB           $CNB_START
      PACK          $PACK_START
      Native-CNB    $NATIVE_CNB_START
      Native        $NATIVE_START
      Native-x      $NATIVE_X_START" \
      | termgraph --title "App Start Time" --width 60  --color cyan --suffix " ms"
