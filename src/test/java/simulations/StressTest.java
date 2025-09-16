package simulations;

import java.time.Duration;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StressTest extends Simulation {

  private static final String BASE_URL = "http://localhost:8080/TaskManagerApp";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl(BASE_URL)
    .inferHtmlResources()
    .shareConnections() 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en,en-US;q=0.9,he;q=0.8")
    .upgradeInsecureRequestsHeader("1");

  private ScenarioBuilder scn = scenario("TaskManagerStressTest")
    .exec(
      http("add_task_1").post("/index.jsp").formParam("task", "go to the supermarket"),
      pause(8),
      http("add_task_2").post("/index.jsp").formParam("task", "do homework"),
      pause(3),
      http("toggle_task_0").get("/index.jsp?toggle=0")
    );

  {
    setUp(
      scn.injectOpen(
       
        constantUsersPerSec(100).during(Duration.ofSeconds(20)),
        constantUsersPerSec(200).during(Duration.ofSeconds(20)),
        constantUsersPerSec(300).during(Duration.ofSeconds(20)),
        constantUsersPerSec(400).during(Duration.ofSeconds(20)),
        rampUsersPerSec(400).to(300).during(Duration.ofSeconds(20)),
        rampUsersPerSec(300).to(200).during(Duration.ofSeconds(20)),
        rampUsersPerSec(200).to(100).during(Duration.ofSeconds(20)),
        constantUsersPerSec(100).during(Duration.ofSeconds(20)),

        
        nothingFor(Duration.ofSeconds(20)),

        constantUsersPerSec(100).during(Duration.ofSeconds(20)),
        constantUsersPerSec(200).during(Duration.ofSeconds(20)),
        constantUsersPerSec(300).during(Duration.ofSeconds(20)),
        constantUsersPerSec(400).during(Duration.ofSeconds(20)),
        rampUsersPerSec(400).to(300).during(Duration.ofSeconds(20)),
        rampUsersPerSec(300).to(200).during(Duration.ofSeconds(20)),
        rampUsersPerSec(200).to(100).during(Duration.ofSeconds(20)),
        constantUsersPerSec(100).during(Duration.ofSeconds(20))
      )
    )
    .protocols(httpProtocol)
    .maxDuration(Duration.ofMinutes(6));

  }
}
