# **Spring Boot ELK Stack Example**

<h3>Overview</h3>

In this project we are getting spring boot integration with logstash and ELK Stack via log4j2.

#

<h3>Integration</h3>

First of all we need to add required dependencies in pom.xml file.

````
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
     <exclusion>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-logging</artifactId>
     </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
````

After adding dependencies, now we can configure log4j2.xml file in resources folder under src/main. I used file appender,console appender and logstash appender. If you want to use logstash only you can remove other appenders. 

````
<Configuration>
    <Properties>
        <Property name="logPath">logs/elk-example/</Property>
        <Property name="rollingFileName">elk-example_app</Property>
        <Property name="defaultPattern">[%highlight{%-5level}] %d{DEFAULT} %c{1}.%M() - %msg%n%throwable{short.lineNumber}</Property>
        <Property name="logStashPattern">[%d{ISO8601}][%-5p][%-25c]%notEmpty{[%X{pipeline.id}]}%notEmpty{[%X{plugin.id}]} %m%n]</Property>
    </Properties>
    <Appenders>
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="${defaultPattern}" />
        </Console>
        <RollingFile name="rollingFile" fileName="${logPath}/${rollingFileName}.log" filePattern="${logPath}/${rollingFileName}_%d{yyyy-MM-dd}.log">
            <PatternLayout pattern="${defaultPattern}" />
            <Policies>
                <OnStartupTriggeringPolicy />
                <TimeBasedTriggeringPolicy interval="1" modulate="true" />
            </Policies>
        </RollingFile>
        <Socket name="socket" host="${sys:logstash.host.name:-localhost}" port="${sys:logstash.port.number:-9999}" reconnectionDelayMillis="5000">
            <PatternLayout pattern="${defaultPattern}" />
        </Socket>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="console" />
            <AppenderRef ref="rollingFile"/>
            <AppenderRef ref="socket"/>
        </Root>
    </Loggers>
</Configuration>
```` 

The appender with type socket helps us to send logs to logstash. For the integration logstash, you need only this socket appender. Host name and port number must be specified in socket appender. I added a docker-compose.yml file in the project base directory and used 9999 as port for logstash. You can change it in project base directory /volumes/logstash/logstash.conf file. If you change the port number in config file don't forget chancing exposed port number from docker-compose file.

After doing given steps now you are ready for integration logstash.

# Building Containers

If you already installed docker just run `$ docker-compose build` command.

# Running Containers

Containers builded successfully then you can start containers with `$ docker-compose up` command.

http://localhost:5601 => You can visualize logs at ELK Stack.

Open the kibana and click discover menu. First of all you should add index pattern. Then open discover panel again. 

Hit the application rest endpoint with `$ curl http://localhost:8080`. 

Now you can see Hello world message in Kibana discover panel.

###### Contact
For any question;
 
Mail : karadenizfaruk28@gmail.com

Linkedin :  https://www.linkedin.com/in/faruk-karadeniz/ 
