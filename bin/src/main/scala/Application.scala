// SimpleServerLoop.java: a simple server program that runs forever in a single thead
import java.net._
import java.io._
import resource._
import scala.concurrent.ExecutionContext
object SimpleServerLoop extends App {
  def execute[A, B](s :A)(body: A => B) =
    ExecutionContext.global.execute(
      new Runnable {
        def run() = body(s)
      }    
    )
  def execute(body: => Unit) = ExecutionContext.global.execute(
    new Runnable {
      def run() = body
    }    
  )
  //setting up upd sever
  execute {
    val aSocket = new DatagramSocket(6789);
    val buffer: Array[Byte] = Array.ofDim[Byte](1000);
    //upd thread
    while (true) {
      val request: DatagramPacket = new DatagramPacket(buffer, buffer.length);
      aSocket.receive(request);//blocking
      execute(request) {
        request =>
           //1. read a word
          val data = request.getData()
          val word = new String(data).trim()
          //2. match data
          //3. sent meaning back
          
      }

    }

  }
  //setting up TCP
  for (serverSocket <- managed(new ServerSocket(1234))) {
    while (true) {
      try {
        //accepting a socket
          execute(serverSocket.accept()) {
            isocket: Socket => {
            for (
              socket <- managed(isocket);
              is <- managed(socket.getInputStream);
              dataIS <- managed(new DataInputStream(is));
              os <- managed(socket.getOutputStream);
              dataOS <- managed(new DataOutputStream(os))
            ) {
              //1. read a word
              val word = dataIS.readUTF()
              //2. match meaning
              word.trim().toLowerCase() match {
                case "guru" =>
                  //3. sent meaning back to client
                  dataOS.writeUTF("master")
                case "love" =>
                  dataOS.writeUTF("love is feeling")
                case _ =>
                  dataOS.writeUTF("dont know")
              }
  
            }
            }
          }        
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }
  }
}

