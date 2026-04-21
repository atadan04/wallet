package org.komlev.wallet;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CommonDatabaseIT{
    @ServiceConnection
    static final PostgreSQLContainer<?> DB_INSTANCE = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testDb")
            .withUsername("testUser")
            .withPassword("test");

//    @DynamicPropertySource
//    static void registerPgProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//
//    }
    static {
        DB_INSTANCE.start();
    }
}
