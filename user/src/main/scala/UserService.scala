
import com.sutemi.example.lightning._
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.finagle.builder.ServerBuilder
import java.net.InetSocketAddress

object UserService {

	def main(args: Array[String]) {

		val processor = new UserServiceImpl

		val service = new UserRpc.FinagledService(processor,
			new TBinaryProtocol.Factory())

		ServerBuilder()
			.bindTo(new InetSocketAddress(8080))
			.codec(ThriftServerFramedCodec())
			.name("UserService")
			.build(service)
	}
}

class UserServiceImpl extends UserRpc.FutureIface {
  def getUser() = {
    val user = User(Some("Chris"), Some("chris@chris.com"))
    Future.value(user)
  }
}

