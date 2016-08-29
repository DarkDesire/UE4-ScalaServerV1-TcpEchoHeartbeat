package frontend

import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, Props}
import akka.io.Tcp
import akka.io.Tcp.{PeerClosed, Received, Write}
import akka.util.ByteString

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object TcpConnection {
  def props(id: Long, connection: ActorRef) = Props(new TcpConnection(id, connection))
  // ----- heartbeat -----
  // Checking client connection for life
  case object Heartbeat
  val period = 1 seconds
  // Sending message to client
  case class Send(data: Array[Byte])
}

class TcpConnection(val id: Long, connection: ActorRef) extends Actor with ActorLogging {
  import TcpConnection._

  def receive = {
    case Send(data) => sendData(data)

    case Heartbeat => sendHeartbeat()

    case Received(data) => receiveData(data)

    case PeerClosed => context stop self

    case _: Tcp.ConnectionClosed => context stop self

    case _ => log.info("unknown message")

  }

  // ----- actor -----
  override def preStart() {
    // initialization code
    scheduler = context.system.scheduler.schedule(period, period, self, Heartbeat)

    log.info("Session start: {}", toString)
  }

  // ----- heartbeat -----
  private var scheduler: Cancellable = _

  // ----- actions -----
  def receiveData(data: ByteString) {
    val received = data.utf8String

    log.info(s"Received: ${received}")

    val output = s"[Echo from server]: ${received}"

    sendData(output.getBytes)

  }

  def sendData(data: Array[Byte]) = {

    val msg: ByteString = ByteString(data)
    connection ! Write(msg)

    log.info(s"Msg send: {${msg.utf8String}}")
  }

  def sendHeartbeat(): Unit = {
    val heartbeat = "Hearbeat".getBytes
    sendData(heartbeat)
  }

}

