# UE4-ScalaServerV1-TcpEchoHeartbeat
This is a simple tcp server is written on scala (most part of code **akka.actors** and **akka.io**)

Address: **127.0.0.1:8080**

How it works. 
- [echo] You send smth on this ip and server sends it back to you
- [heartbeat] After your connection server every 1s sends you "Heartbeat"

On server we create actor per each request. 

Client can find here: [DarkDesire/UE4-TcpExamle](https://github.com/DarkDesire/UE4-TcpExamle)

##In case if you are in root directory
For starting type in cmd : **java -jar UE4-ScalaServerV1-TcpEchoHeartbeat-assembly-1.0.jar**

## Or you can compile code by yourself and then type another command:
**sbt assembly**


# Example
![Example](http://storage7.static.itmages.ru/i/16/0829/h_1472442842_6421506_8e814fca4d.png)
