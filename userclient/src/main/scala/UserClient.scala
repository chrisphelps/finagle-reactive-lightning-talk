import java.net.InetSocketAddress
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.Service
import com.twitter.finagle.CodecFactory
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftClientRequest}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.Thrift


import com.sutemi.example.user._

object UserClient {
	def main(args: Array[String]) {
		val port = 8080

		val service: Service[ThriftClientRequest, Array[Byte]] = 
			ClientBuilder()
				.hosts(new InetSocketAddress(port))
				.codec(ThriftClientFramedCodec())
				.hostConnectionLimit(1)
				.build()

		val client = new UserRpc.FinagledClient(service, new TBinaryProtocol.Factory())

		client.getUser() onSuccess { message => println("received response: User.name: " + message.name.get) }
		//ensure { service.close() }

	}
}

