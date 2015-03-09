package billslacum

import java.io.{BufferedReader, File, FileInputStream, InputStreamReader}
import java.net._
import java.nio.channels.SocketChannel

/**
 * A sample application demonstrating zero-copy file transfer from a client
 * to a server.
 *
 * Note that the life cycle of certain objects isn't fully managed, as this is
 * a demo application. Specifically the server socket is never closed and there
 * is no error handling.
 */
object ZeroCopy extends App {

  val serverThread = new Thread(new Runnable() {
    override def run: Unit = {
      val sc = new ServerSocket(1234)
      while(true) {
        val s = sc.accept()
        val reader = new BufferedReader(new InputStreamReader(s.getInputStream))
        val blob = Stream.continually(reader.readLine()).takeWhile(_ != null)
        System.out.println(blob)
        s.close()
      }
    }
  })
  serverThread.start()

  val file = new File(getClass.getResource("/lorem-ipsum.txt") toURI)
  val inChannel = new FileInputStream(file).getChannel()
  val connection = SocketChannel.open()
  connection.connect(new InetSocketAddress(1234))
  inChannel.transferTo(0, file length, connection)
  inChannel.close()
  connection.close()
}