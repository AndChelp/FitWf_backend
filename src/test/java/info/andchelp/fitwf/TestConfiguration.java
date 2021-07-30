package info.andchelp.fitwf;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.util.SocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = TestConfiguration.DockerPostgreDataSourceInitializer.class)
public abstract class TestConfiguration {

    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.1"))
            .withDatabaseName("dbname_" + RandomStringUtils.randomAlphabetic(3, 6))
            .withUsername("uname_" + RandomStringUtils.randomAlphabetic(3, 6))
            .withPassword("pass_" + RandomStringUtils.randomAlphabetic(3, 6))
            .waitingFor(Wait.forListeningPort());

    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            postgresDB.start();
            int port = SocketUtils.findAvailableTcpPort();
            RestAssured.port = port;
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "auto.generated.port=" + port,
                    "auto.generated.jdbc.url=" + postgresDB.getJdbcUrl(),
                    "auto.generated.username=" + postgresDB.getUsername(),
                    "auto.generated.password=" + postgresDB.getPassword()
            );
        }
    }
}