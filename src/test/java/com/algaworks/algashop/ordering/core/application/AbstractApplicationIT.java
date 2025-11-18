package com.algaworks.algashop.ordering.core.application;

import com.algaworks.algashop.ordering.utils.TestcontainerPostgreSQLConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

//@Testcontainers
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainerPostgreSQLConfig.class)
public abstract class AbstractApplicationIT {

//    @Container
//    @ServiceConnection
//    protected static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

}