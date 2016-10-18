##changjiudai bit target months monitor

####1. setup init.properties
```
#for target month
P.monitor.month=6 
#for update minutes
P.update.minutes=10

#if need proxy you can setup system proxy params:
http.proxyHost=shtmg.xxx.com
http.proxyPort=8080
```


####2. compile
```
javac -cp jsoup-1.8.3.jar TestGetContent.java
```

####3. run & monitor
```
java -classpath jsoup-1.8.3.jar; TestGetContent
```


