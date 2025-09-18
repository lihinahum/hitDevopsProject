# DevOps Project - Jenkins CI/CD Pipeline

## Project Summary
A simple Task Manager web app (Java/JSP on Tomcat) with a complete CI/CD pipeline in Jenkins.  
On each change, the pipeline builds from GitHub, deploys to Tomcat, verifies health, runs a quick UI smoke test, and executes Gatling performance tests.  
This repo includes the Gatling simulations and screenshots for review.

## CI/CD Pipeline (Jenkins)
1) **Build & Deploy**  
   Checks out from GitHub, packages and deploys to Tomcat under `/TaskManagerApp`, and reloads if needed- delivering a fresh version at `http://localhost:8080/TaskManagerApp`.

   <img width="524" height="510" alt="image" src="https://github.com/user-attachments/assets/c14160f8-86c1-4932-b0bc-c3db428682bb" />
   <img width="489" height="583" alt="image" src="https://github.com/user-attachments/assets/78066bde-a3ed-49c5-81ab-9256d80276bc" />

3) **Health Check (Availability)**
Pings the site URL and expects 200 OK; otherwise the job fails immediately.

   <img width="1710" height="175" alt="image" src="https://github.com/user-attachments/assets/644a9323-7d83-467e-ad7c-6765236dcbdd" />


5) **UI Smoke Test (Selenium IDE)**
   Opens the app in a browser, adds two tasks, and toggles one. If these steps work, the build passes.
   
   <img width="542" height="409" alt="image" src="https://github.com/user-attachments/assets/93e2ec28-d7d3-4f33-bae3-aa89338e7d89" />


7) **Performance Tests (Gatling)**
   **Capacity note:** Max-load showed the app can hold about 3,900 users at once.
   So the load test runs at ~90% of that (~3,500 users) - close to peak, without overloading.
   
   - **Load (5 min, closed model):** trapezoid **1–3–1** at ~**90% of capacity** (concurrent users).
     
     <img width="1263" height="460" alt="image" src="https://github.com/user-attachments/assets/992f7ce2-47cd-420a-af4b-4d5b3837c455" />
     <img width="1248" height="412" alt="image" src="https://github.com/user-attachments/assets/df46b338-2a30-407d-a130-5673a4a034ad" />


   - **Stress (2 cycles):** step pattern **100→200→300→400** users/sec with **tier-down**; verifies recovery.
     
     <img width="1260" height="422" alt="image" src="https://github.com/user-attachments/assets/52ab12dd-c6ee-4686-8247-3374fd6bf236" />
     <img width="1237" height="420" alt="image" src="https://github.com/user-attachments/assets/b6c39175-e82b-4718-86c1-061d7c01512f" />



From commit to verified release: build → deploy → health → UI → performance, all automated and reproducible.
