##Cricinfo Curler

A simple command line tool for getting the cricket scores in easy way for systems which does not have `curl` function or for those who does not have knowledge about `curl`

![Screen Shots](cricinfocurler.gif?raw=true )
###To Make changes and Build
* Clone the project and import into eclipse
* Run-->Build_JAR.xml as `ANT build`

###Direct use of existing JAR
* Use the CricInfoCurler.jar present in the project root folder

###Tool Usage
#### Help on Usage
* Execute below command for usage or help<br>
`java -jar CricInfoCurler.jar -h`
####Add Match configurations
* Execute command as below<br>
`java -jar CricInfoCurler.jar -m <espncricinfo matchurl> -p <are u behind proxy "true" or "false" -pa <if "true" Proxy IP Address Proxy IP Port>  -a`

####View Scores
* Execute the below command each time <br>
`java -jar CricInfoCurler.jar-s`
Updated
