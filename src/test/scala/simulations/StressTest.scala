package simulations
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StressTestSimulation extends Simulation {
  val httpProtocol = http
    .baseUrl("http://localhost:8080/LihiMayaDorAndGalApp")
    .acceptHeader("text/html")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) Firefox/113.0")
    
  val scn = scenario("StressTestSimulation")
    // Just visit the homepage - simulating a user accessing your application
    .exec(http("home_page").get("/"))
    
  setUp(
    scn.inject(
      // Start at 100% of sustainable capacity (~450 users/sec)
      constantUsersPerSec(450).during(60.seconds),
      
      // Increase to 125% of sustainable capacity (~560 users/sec)
      rampUsersPerSec(450).to(560).during(30.seconds),
      constantUsersPerSec(560).during(30.seconds),
      
      // Push to 150% of sustainable capacity (~675 users/sec)
      rampUsersPerSec(560).to(675).during(30.seconds),
      constantUsersPerSec(675).during(30.seconds),
      
      // Final surge to 175% of sustainable capacity (~790 users/sec)
      rampUsersPerSec(675).to(790).during(30.seconds),
      constantUsersPerSec(790).during(30.seconds)
    )
  ).protocols(httpProtocol)
   .maxDuration(240.seconds) // 4 minutes
}