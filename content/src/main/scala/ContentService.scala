import com.sutemi.example.content._
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.finagle.builder.ServerBuilder
import java.net.InetSocketAddress

object ContentService {

  def main(args: Array[String]) {

    val processor = new ContentServiceImpl

    val service = new ContentRpc.FinagledService(processor,
      new TBinaryProtocol.Factory())

    ServerBuilder()
      .bindTo(new InetSocketAddress(8082))
      .codec(ThriftServerFramedCodec())
      .name("ContentService")
      .build(service)
  }
}

class ContentServiceImpl extends ContentRpc.FutureIface {
  def getContent() = {
    val content = Content(Some("title"), Some("This is a full title"), Some("Unpersonalized Content"))
    Future.value(content)
  }
}
