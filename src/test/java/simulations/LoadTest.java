package simulations;

import java.time.Duration;
import java.util.Map;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LoadTest extends Simulation {

 
  private static final String BASE_URL = "http://localhost:8080/TaskManagerApp";
  private static final int    MAX_CAPACITY = 3500;            
  private static final int    TARGET_CONCURRENT = (int) Math.ceil(MAX_CAPACITY * 0.90); // 90%

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl(BASE_URL)
    .inferHtmlResources()
    .shareConnections() 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en,en-US;q=0.9,he;q=0.8")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");

  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Origin", BASE_URL),
    Map.entry("Pragma", "no-cache"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1")
  );

  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Pragma", "no-cache"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1")
  );

  private ScenarioBuilder scn = scenario("TaskManagerLoadTest")
    .exec(
      http("request_0").post("/index.jsp").headers(headers_0)
        .formParam("task", "go to the supermarket"),
      pause(8),
      http("request_1").post("/index.jsp").headers(headers_0)
        .formParam("task", "do homework"),
      pause(3),
      http("request_2").get("/index.jsp?toggle=0").headers(headers_2)
    );

  {
   
    setUp(
      scn.injectClosed(
        rampConcurrentUsers(1).to(TARGET_CONCURRENT).during(Duration.ofMinutes(1)),
        constantConcurrentUsers(TARGET_CONCURRENT).during(Duration.ofMinutes(3)),
        rampConcurrentUsers(TARGET_CONCURRENT).to(1).during(Duration.ofMinutes(1))
      )
    )
    .protocols(httpProtocol)
    .maxDuration(Duration.ofMinutes(6));

  }
}
