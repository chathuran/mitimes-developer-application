# mitimes-developer-application

This is a Spring Boot application designed to interact with the MiTimes career application API.

## Prerequisites

-   Java 25 or higher
-   Maven (or use the provided `mvn` wrapper)

## Configuration

The application's API endpoints are configured in `src/main/resources/application.yaml`:

```yaml
api:
  secret-url: https://au.mitimes.com/careers/apply/secret
  apply-url: https://au.mitimes.com/careers/apply
```

## How to Run

You can run the application using the Maven wrapper included in the project.

### From the Terminal (macOS/Linux)

1.  Open your terminal.
2.  Navigate to the project root directory.
3.  Run the following command:

    ```bash
    mvn spring-boot:run
    ```

### From the Terminal (Windows)

1.  Open your command prompt or PowerShell.
2.  Navigate to the project root directory.
3.  Run the following command:

    ```cmd
    mvn spring-boot:run
    ```

### From an IDE (IntelliJ IDEA / Android Studio)

1.  Open the project in your IDE.
2.  Locate the main class: `com.chathuran.mitimes_developer_app.MitimesDeveloperAppApplication`.
3.  Right-click the class and select **Run 'MitimesDeveloperAppApplication'**.

## Logging

The application logs its progress (fetching the secret token, generating the JSON body, and submitting the application) to the console using SLF4J and Logback.
