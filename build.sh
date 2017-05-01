#!/bin/bash

echo checking arguments
ARG1="dummy"
if [[ $# -eq 0 ]]; then
  echo no argument supplied. will not restart tomcat
else
  ARG1=$1
fi

mvn install

echo removing existing war file from machine
rm /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfldraft.war

echo renaming new build
mv /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfl-draft-notes-app-0.0.1-SNAPSHOT.war /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfldraft.war

if [[ $ARG1 = "stopserver" ]]; then
  echo stopping remote tomcat
  ssh -p 1033 -i /Users/lancefallon/.ssh/id_rsa.1488030491 nflcapsi@173.243.120.226 /home/nflcapsi/appservers/apache-tomcat-7.0.68/bin/shutdown.sh
fi

echo deleting remote nfldraft application
ssh -p 1033 -i /Users/lancefallon/.ssh/id_rsa.1488030491 nflcapsi@173.243.120.226 'rm -rf /home/nflcapsi/appservers/apache-tomcat-7.0.68/webapps/nfldraft*'

echo copying new build to remote
scp -i /Users/lancefallon/.ssh/id_rsa.1488030491 -P 1033 /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfldraft.war nflcapsi@173.243.120.226:/home/nflcapsi/appservers/apache-tomcat-7.0.68/webapps

if [[ $ARG1 = "stopserver" ]]; then
  echo restarting remote tomcat
  ssh -p 1033 -i /Users/lancefallon/.ssh/id_rsa.1488030491 nflcapsi@173.243.120.226 /home/nflcapsi/appservers/apache-tomcat-7.0.68/bin/startup.sh
fi
