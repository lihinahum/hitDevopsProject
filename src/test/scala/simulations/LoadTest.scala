package simulations
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest extends Simulation {
  val httpProtocol = http
    .baseUrl("http://localhost:8080/LihiMayaDorAndGalApp")
    .acceptHeader("text/html")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) Firefox/113.0")
    
  val scn = scenario("TaskManagerLoadTest")
    .exec(http("HomePage").get("/"))
    
  // 80% of established max (600 users/sec)
  val targetUsers = 480
  
  setUp(
    scn.inject(
      constantUsersPerSec(200).during(30.seconds),
      rampUsersPerSec(200).to(targetUsers).during(30.seconds),
      constantUsersPerSec(targetUsers).during(240.seconds)
    )
  ).protocols(httpProtocol)
   .maxDuration(5.minutes)
}