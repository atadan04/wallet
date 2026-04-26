package org.komlev.wallet;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public abstract class CommonIT extends CommonDatabaseIT {

    @LocalServerPort
    protected int localPort;
    @Autowired
    protected TestRestTemplate testRestTemplate;

    protected <Req, Res> ResponseEntity<Res> sendPost(String path, Req req, Class<Res> responseClass) {
        return testRestTemplate.postForEntity("http://localhost:" + localPort + path, req, responseClass);
    }

}
