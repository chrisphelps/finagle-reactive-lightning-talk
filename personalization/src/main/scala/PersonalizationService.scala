import com.sutemi.example.lightning._
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.finagle.builder.ServerBuilder
import java.net.InetSocketAddress

object PersonalizationService {

  def main(args: Array[String]) {

    val processor = new PersonalizationServiceImpl

    val service = new PersonalizationRpc.FinagledService(processor,
      new TBinaryProtocol.Factory())

    ServerBuilder()
      .bindTo(new InetSocketAddress(8083))
      .codec(ThriftServerFramedCodec())
      .name("PersonalizationService")
      .build(service)
  }
}

class PersonalizationServiceImpl extends PersonalizationRpc.FutureIface {
  def personalizeContent(incontent: Content, user:User) = {
    val content = Content(incontent.title, incontent.description, Some("Personalized for " + user.name))
    Future.value(content)
  }
}
