
import com.sutemi.example.hello._
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.finagle.builder.ServerBuilder
import java.net.InetSocketAddress

object UserService {

	def main(args: Array[String]) {
		//val server = Thrift.serveIface("localhost:8080", new Hello[Future] {
		//	def hi() = Future.value("hi")
		//})
		//Await.ready(server)

		//val service = Thrift.serve("localhost:8080", new HelloServiceImpl)
		
		val processor = new HelloServiceImpl

		val service = new HelloService.FinagledService(processor,
			new TBinaryProtocol.Factory())

		ServerBuilder()
			.bindTo(new InetSocketAddress(8080))
			.codec(ThriftServerFramedCodec())
			.name("UserService")
			.build(service)
	}
}

class HelloServiceImpl extends HelloService.FutureIface {
	def hi() = Future.value("hi")
}

