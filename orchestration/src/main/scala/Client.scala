import java.net.InetSocketAddress
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.Service
import com.twitter.finagle.CodecFactory
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftClientRequest}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.Thrift


import com.sutemi.example.lightning._

object Client {
	def main(args: Array[String]) {
		val userport = 8080
    val tenantport = 8081
    val contentport = 8082
    val personalizationport = 8083

		val userService = buildService(userport)
    val tenantService = buildService(tenantport)

		val userclient = new UserRpc.FinagledClient(userService, new TBinaryProtocol.Factory())
		val tenantclient = new TenantRpc.FinagledClient(tenantService, new TBinaryProtocol.Factory())

		userclient.getUser() onSuccess { message => println("received user response: User.name: " + message.name.get) }
		tenantclient.getTenant() onSuccess { message => println("received tenant response: Tenant.name: " + message.name.get) }
		//ensure { service.close() }

	}

  def buildService(port: Int): Service[ThriftClientRequest, Array[Byte]] = {
    ClientBuilder()
      .hosts(new InetSocketAddress(port))
      .codec(ThriftClientFramedCodec())
      .hostConnectionLimit(1)
      .build()
  }
}

