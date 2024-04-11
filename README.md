The **AMQPTestClientApp** project implements a simple application for testing AMQP 1.0 messaging providers. This application enables sending or receiving test AMQP messages.

In the *target* folder you'll find an executable Java jar file containing the current application version. It should be executed like:
```
java -jar AMQPTestClient.jar -option1 value1 -option2 value2 ...
```
Below is a short description of the command line options:
```
usage: AMQP Test Client
 -h,--host <arg>         hostname
 -inF,--inFile <arg>     input file name
 -m,--mode <arg>         sender/receiver mode
 -outF,--outFile <arg>   output file name
 -p,--port <arg>         port
 -q,--queue <arg>        queue name
 -t,--topic <arg>        topic name
```
The option *-m* defines if you're sending or receiving messages. This option is mandatory and has 2 values - *send* or *receive*.
The options *-h* and *-p* define the connection parameter for the AMQP messaging provider - hostname and port. If these options are missing the default values are used - *localhost* and *5672*.
The options *-t* or *-q* define the topic or queue name used for communication. 
If you're sending message, you need to provide an input file for the message content: option *-inF*.
In case of receiving messages, you can provide a file name where all received messages will be stored: option *-outF*.
