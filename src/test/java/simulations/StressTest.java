package simulations;
import java.time.Duration;
import java.util.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class StressTest extends Simulation {
  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:8080")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en,en-US;q=0.9,he;q=0.8")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Origin", "http://localhost:8080"),
    Map.entry("Pragma", "no-cache"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"136\", \"Google Chrome\";v=\"136\", \"Not.A/Brand\";v=\"99"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Pragma", "no-cache"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"136\", \"Google Chrome\";v=\"136\", \"Not.A/Brand\";v=\"99"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows")
  );
  
 
  private ScenarioBuilder scn = scenario("TaskManagerStressTest")
    .exec(
      http("request_0")
        .post("/LihiMayaDorAndGalApp/index.jsp")
        .headers(headers_0)
        .formParam("task", "go to the supermarket"),
      pause(8),
      http("request_1")
        .post("/LihiMayaDorAndGalApp/index.jsp")
        .headers(headers_0)
        .formParam("task", "do homework"),
      pause(3),
      http("request_2")
        .get("/LihiMayaDorAndGalApp/index.jsp?toggle=0")
        .headers(headers_2)
    );
  
  {
   {
  {
  // Stress test with step-pattern as shown in the diagram
  setUp(
    scn.injectOpen(
      constantUsersPerSec(100).during(Duration.ofSeconds(45)),
      
      // Step 2: Medium load level
      constantUsersPerSec(200).during(Duration.ofSeconds(45)),
      
      // Step 3: Approaching max capacity
      constantUsersPerSec(300).during(Duration.ofSeconds(45)),
      
      // Final step: At max capacity (not exceeding it too much)
      constantUsersPerSec(400).during(Duration.ofSeconds(45))
    )
  ).protocols(httpProtocol)
   .maxDuration(Duration.ofMinutes(3));
}
}
  }
}