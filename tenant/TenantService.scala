import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Await, Future}
import java.net.InetSocketAddress
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.buffer.ChannelBuffers

object TenantService extends App {
  
  val service = new Service[HttpRequest, HttpResponse] {
    def apply(req: HttpRequest): Future[HttpResponse] = {
      val resp = new DefaultHttpResponse(req.getProtocolVersion, HttpResponseStatus.OK)
   	  resp.setContent(ChannelBuffers.copiedBuffer("{ \"tenantId\":\"12345\"}", "UTF-8"))
      
      Future.value(resp)
    }
  }

  val server = Http.serve(":8080", service)
  Await.ready(server)
}
