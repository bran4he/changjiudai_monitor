###changjiudai bit target months monitor

1.setup init.properties
P.monitor.month for target month
P.update.minutes for update minutes

>if need proxy you can setup system proxy params:
- http.proxyHost=shtmg.ebaotech.com
- http.proxyPort=8080

2. compile
```
javac -cp jsoup-1.8.3.jar TestGetContent.java
```

3. run & monitor
```
java -classpath jsoup-1.8.3.jar; TestGetContent
```


