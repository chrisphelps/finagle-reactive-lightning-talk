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
    val contentService = buildService(contentport)
    val personalizeService = buildService(personalizationport)

		val userclient = new UserRpc.FinagledClient(userService, new TBinaryProtocol.Factory())
		val tenantclient = new TenantRpc.FinagledClient(tenantService, new TBinaryProtocol.Factory())
		val contentclient = new ContentRpc.FinagledClient(contentService, new TBinaryProtocol.Factory())
    val personalizeclient = new PersonalizationRpc.FinagledClient(personalizeService, new TBinaryProtocol.Factory())

    // direct flatMap
    // val userfuture = userclient.getUser()
    // val contentfuture =
    //   tenantclient.getTenant() flatMap {
    //     t => contentclient.getContent(t)
    //   } flatMap {
    //     c => userfuture flatMap {
    //       u => personalizeclient.personalizeContent(c, u)
    //     }
    //   }

    val contentfuture = for {
      user <- userclient.getUser()
      tenant <- tenantclient.getTenant()
      content <- contentclient.getContent(tenant)
      personalized <- personalizeclient.personalizeContent(content, user)
    } yield personalized


		//userfuture onSuccess { message => println("received user response: User.name: " + message.name.get) }
    contentfuture onSuccess { message => println("received content response. Content.customtext: " + message.customtext.get) }

	}

  def buildService(port: Int): Service[ThriftClientRequest, Array[Byte]] = {
    ClientBuilder()
      .hosts(new InetSocketAddress(port))
      .codec(ThriftClientFramedCodec())
      .hostConnectionLimit(1)
      .build()
  }
}

