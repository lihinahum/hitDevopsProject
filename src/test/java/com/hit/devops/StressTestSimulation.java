package com.hit.devops;

import java.time.Duration;
import java.util.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class StressTestSimulation extends Simulation {
  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://216e-2a06-c701-487e-5c00-1900-eda-c68e-a5be.ngrok-free.app")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en,en-US;q=0.9,he;q=0.8")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");
    
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("pragma", "no-cache"),
    Map.entry("priority", "u=0, i"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"136\", \"Google Chrome\";v=\"136\", \"Not.A/Brand\";v=\"99"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows"),
    Map.entry("sec-fetch-dest", "document"),
    Map.entry("sec-fetch-mode", "navigate"),
    Map.entry("sec-fetch-site", "same-origin"),
    Map.entry("sec-fetch-user", "?1")
  );
    
  // Add some think time and additional requests to make the test more realistic
  private ScenarioBuilder scn = scenario("StressTestSimulation")
    .exec(
      http("home_page")
        .get("/LihiMayaDorAndGalApp/index.jsp?toggle=1")
        .headers(headers_0)
        .check(status().is(200))
    )
    .pause(1, 3) // Random pause between 1-3 seconds to simulate user behavior
    .exec(
      http("refresh_page")
        .get("/LihiMayaDorAndGalApp/index.jsp?toggle=1")
        .headers(headers_0)
        .check(status().is(200))
    );
    
  {
    // True stress test configuration
    setUp(
      scn.injectClosed(
        // Short warmup - just to establish baseline
        rampConcurrentUsers(1).to(50).during(Duration.ofSeconds(30)),
        
        // Begin serious stress - continuous ramping higher and higher
        rampConcurrentUsers(50).to(200).during(Duration.ofSeconds(60)),
        rampConcurrentUsers(200).to(500).during(Duration.ofSeconds(60)),
        rampConcurrentUsers(500).to(1000).during(Duration.ofSeconds(60)),
        
        // Maintain extreme load to observe system behavior under sustained stress
        constantConcurrentUsers(1000).during(Duration.ofSeconds(60))
      ).protocols(httpProtocol)
    );
  }
}