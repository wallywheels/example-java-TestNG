### Integration example TestNG+logback

Java stack example. 

* contains structure integration - agent. TestNG Listener
* logging integration with LogBack
* screenshot attachement

Download this example with:

```shell
git clone https://github.com/reportportal/example-java-TestNG-logback.git
```

  1. Open `pom.xml` and un-comment 
    - repository
    - dependencies
    - properties - listener
  2. Open `logback.xml` and un-comment 
    - appender RP
    - logger - `binary_data_logger`
    - appender-ref RP
  3. Update `reportportal.properties` file according to you profile page
  4. Navigate to folder with `pom.xml`
  5. call `mvn clean test`


Detailed documentation available here:
  - http://reportportal.io/#documentation/TestNG
  - http://reportportal.io/#documentation/Logback


