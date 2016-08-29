import akka.actor.{ActorSystem, Props}
import frontend.AkkaIoTcpServer

/**
  * Created by Eldar on 29.08.2016.
  */
object Starter extends App{
  val actorSystem = ActorSystem("system")
  val actorTcpServer = actorSystem.actorOf(AkkaIoTcpServer.props("127.0.0.1",8080),"TcpServer")
}
