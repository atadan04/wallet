package org.komlev.wallet;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CommonDatabaseIT {

    @ServiceConnection
    static final PostgreSQLContainer<?> DB_INSTANCE = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("walletTest")
            .withUsername("test")
            .withPassword("test")
            .withReuse(false);

    static {
        DB_INSTANCE.setPortBindings(List.of("5433:5432"));
        DB_INSTANCE.start();
    }
}
