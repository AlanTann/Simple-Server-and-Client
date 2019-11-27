import java.net._;
import java.io._;
import resource._
object SimpleClient extends App {
    try{
      // Open your connection to a server, at port 1234
      for (s1 <- managed(new Socket("localhost",1234));
        os <- managed(s1.getOutputStream);
        dos <- managed(new DataOutputStream(os));
        is <- managed(s1.getInputStream);
        dis <- managed(new DataInputStream(is))){
        //send word
        dos.writeUTF("guru")
        //read reply
        val reply = dis.readUTF()
        //output
        println(s"the meaning of guru is $reply")
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  
}
