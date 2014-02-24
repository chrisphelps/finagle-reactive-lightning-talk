import java.net.InetSocketAddress
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.Service
import com.twitter.finagle.CodecFactory
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftClientRequest}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.Thrift


import com.sutemi.example.hello._

object UserClient {
	def main(args: Array[String]) {
		val host = "localhost"
		val port = 8080

		val service: Service[ThriftClientRequest, Array[Byte]] = 
			ClientBuilder()
				.hosts(new InetSocketAddress(port))
				.codec(ThriftClientFramedCodec())
				.hostConnectionLimit(1)
				.build()

		//val client = Thrift.newIface[HelloService.FutureIface]("localhost:8080")
		val client = new HelloService.FinagledClient(service, new TBinaryProtocol.Factory())

		//val helloFuture = client.hi()

		client.hi() onSuccess { message => println("received response: " + message) }
		//ensure { service.close() }

		//Await.result(helloFuture)
	}
}

