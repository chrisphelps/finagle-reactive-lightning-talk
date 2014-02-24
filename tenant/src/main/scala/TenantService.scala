import com.sutemi.example.tenant._
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.finagle.builder.ServerBuilder
import java.net.InetSocketAddress

object TenantService {

  def main(args: Array[String]) {

    val processor = new TenantServiceImpl

    val service = new TenantRpc.FinagledService(processor,
      new TBinaryProtocol.Factory())

    ServerBuilder()
      .bindTo(new InetSocketAddress(8081))
      .codec(ThriftServerFramedCodec())
      .name("TenantService")
      .build(service)
  }
}

class TenantServiceImpl extends TenantRpc.FutureIface {
  def getTenant() = {
    val tenant = Tenant(Some("JavaPosse"), Some("12345"))
    Future.value(tenant)
  }
}
