package info.andchelp.fitwf;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestConfiguration {

    private static final String CREATE_TRUNCATE_SQL = "SELECT 'TRUNCATE ' || string_agg(input_table_name, ', ') || ' CASCADE;' as sql" +
            "    FROM (SELECT table_schema || '.' || table_name AS input_table_name" +
            "    FROM information_schema.tables" +
            "    WHERE table_schema NOT IN ('pg_catalog', 'information_schema')" +
            "    AND table_schema NOT LIKE 'pg_toast%') AS information";

    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.1"))
            .withDatabaseName("dbname_" + RandomStringUtils.randomAlphabetic(3, 6))
            .withUsername("uname_" + RandomStringUtils.randomAlphabetic(3, 6))
            .withPassword("pass_" + RandomStringUtils.randomAlphabetic(3, 6))
            .waitingFor(Wait.forListeningPort());

    @Autowired
    public JdbcTemplate jdbcTemplate;

    protected String TRUNCATE_DATABASE_SQL;

    @BeforeAll
    public final void beforeSpringInit() {
        TRUNCATE_DATABASE_SQL = jdbcTemplate.queryForObject(CREATE_TRUNCATE_SQL, String.class);
    }

    protected void clearDatabase() {
        jdbcTemplate.update(TRUNCATE_DATABASE_SQL);
    }

    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            postgresDB.start();
            RestAssured.port = SocketUtils.findAvailableTcpPort();
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "auto.generated.port=" + RestAssured.port,
                    "auto.generated.jdbc.url=" + postgresDB.getJdbcUrl(),
                    "auto.generated.username=" + postgresDB.getUsername(),
                    "auto.generated.password=" + postgresDB.getPassword()
            );
        }
    }
}