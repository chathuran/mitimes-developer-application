package com.chathuran.mitimes_developer_app.runner;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

    @Value("${api.secret-url}")
    private String secretURL;

    @Value("${api.apply-url}")
    private String applyURL;

    @Override
    public void run(String... args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();


        HttpRequest secretRequest = HttpRequest.newBuilder()
                .uri(URI.create(secretURL))
                .GET()
                .build();

        HttpResponse<String> secretResponse =
                client.send(secretRequest, HttpResponse.BodyHandlers.ofString());

        log.info("Secret Status: {}", secretResponse.statusCode());
        log.info("Secret Body: {}", secretResponse.body());

        String secretToken;
        if (secretResponse.statusCode() == 200) {
            JsonNode secretJson = mapper.readTree(secretResponse.body());
            secretToken = secretJson.get("result").asText();

        }else {
            log.error("Secret value not found");
            return;
        }


        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name", "Chathuran Dannoruwa");
        body.put("email", "chathuran.dc@gmail.com");
        body.put("job_title", "Software Engineer");
        body.put("final_attempt", false);

        Map<String, Object> extraInfo = new LinkedHashMap<>();
        extraInfo.put("experience_years", 8);
        extraInfo.put("skills", new String[]{"Java", "Spring Boot", "Microservices", "AWS", "CI/CD"});
        extraInfo.put("why_you_should_hire_me", "I believe I would be a strong fit for Mitimes because I bring solid engineering experience, a commitment to writing clean and maintainable code, and a genuine interest in the functional technologies your team uses. I have a proven ability to build reliable backend systems, troubleshoot issues efficiently, and collaborate closely with product and engineering teams to deliver user focused solutions. I’m motivated by environments that value learning, ownership, and continuous improvement, and I’m particularly excited about the opportunity to grow my skills. " +
                "Above all, I bring a proactive mindset, clear communication, and a strong sense of responsibility qualities that I believe would allow me to contribute meaningfully to Mitimes’ mission and team culture.");



        //mitimes application Source
        Map<String, Object> meTimesAppInfo = getObjectMap();

        extraInfo.put("mitimes_application", meTimesAppInfo);


        Map<String, Object> personalProjectsInfo = getStringObjectMap();

        extraInfo.put("personal_projects", personalProjectsInfo);

        body.put("extra_information", extraInfo);


        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);

        log.info("JSON:\n{}", json);


        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(applyURL))
                .header("Content-Type", "application/json")
                .header("Authorization", secretToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> postResponse =
                client.send(postRequest, HttpResponse.BodyHandlers.ofString());


        log.info("POST Status: {}", postResponse.statusCode());
        log.info("POST Body: {}", postResponse.body());
    }

    @Nonnull
    private static Map<String, Object> getObjectMap() {
        Map<String, Object> miTimesAppInfo = new LinkedHashMap<>();
        miTimesAppInfo.put("git_repository", "https://github.com/chathuran/mitimes-developer-application");

        Map<String, Object> howToRun = new LinkedHashMap<>();

        Map<String, Object> runUsingMaven = new LinkedHashMap<>();
        runUsingMaven.put("description", "Run the Spring Boot project directly using Maven");
        runUsingMaven.put("command", "mvn spring-boot:run");

        Map<String, Object> buildAndRunJar = new LinkedHashMap<>();
        buildAndRunJar.put("description", "Build the JAR file and run it manually");
        buildAndRunJar.put("build_command", "mvn clean package");
        buildAndRunJar.put("run_command", "java -jar target/mitimes-developer-app-0.0.1-SNAPSHOT.jar");

        howToRun.put("run_using_maven", runUsingMaven);
        howToRun.put("build_and_run_jar", buildAndRunJar);

        miTimesAppInfo.put("how_to_run", howToRun);
        return miTimesAppInfo;
    }

    @Nonnull
    private static Map<String, Object> getStringObjectMap() {
        Map<String, Object> personalProjectsInfo = new LinkedHashMap<>();
        personalProjectsInfo.put("git_repository","https://github.com/chathuran");

        Map<String,Object> pluginInfo = new LinkedHashMap<>();
        pluginInfo.put("plugin_name", "Spring Boot Flow Visualizer");
        pluginInfo.put("plugin_url", "https://plugins.jetbrains.com/plugin/30876-spring-boot-flow-visualizer?noRedirect=true");
        pluginInfo.put("git_repository", "https://github.com/chathuran/api-flow-plugin");

        personalProjectsInfo.put("pluginInfo", pluginInfo);
        return personalProjectsInfo;
    }
}
