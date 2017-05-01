#!/bin/bash

mvn install

echo removing existing war file from machine
rm /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfldraft.war

echo renaming new build
mv /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfl-draft-notes-app-0.0.1-SNAPSHOT.war /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfldraft.war

echo stopping remote tomcat
ssh -p 1033 -i /Users/lancefallon/.ssh/id_rsa.1488030491 nflcapsi@173.243.120.226 /home/nflcapsi/appservers/apache-tomcat-7.0.68/bin/shutdown.sh

echo deleting remote nfldraft application
ssh -p 1033 -i /Users/lancefallon/.ssh/id_rsa.1488030491 nflcapsi@173.243.120.226 'rm -rf /home/nflcapsi/appservers/apache-tomcat-7.0.68/webapps/nfldraft*'

echo copying new build to remote
scp -i /Users/lancefallon/.ssh/id_rsa.1488030491 -P 1033 /Users/lancefallon/.m2/repository/com/lancefallon/usermgmt/nfl-draft-notes-app/0.0.1-SNAPSHOT/nfldraft.war nflcapsi@173.243.120.226:/home/nflcapsi/appservers/apache-tomcat-7.0.68/webapps

echo starting remote tomcat
ssh -p 1033 -i /Users/lancefallon/.ssh/id_rsa.1488030491 nflcapsi@173.243.120.226 /home/nflcapsi/appservers/apache-tomcat-7.0.68/bin/startup.sh
