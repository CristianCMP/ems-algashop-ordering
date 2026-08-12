package com.algaworks.algashop.ordering.core.application;

import com.algaworks.algashop.ordering.utils.MockJwtDecoderConfig;
import com.algaworks.algashop.ordering.utils.TestcontainerPostgreSQLConfig;
import com.algaworks.algashop.ordering.utils.WithMockJwt;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

//@Testcontainers
@WithMockJwt
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestcontainerPostgreSQLConfig.class, MockJwtDecoderConfig.class})
public abstract class AbstractApplicationIT {

//    @Container
//    @ServiceConnection
//    protected static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

//    @MockitoBean
//    protected SecurityCheckApplicationService securityCheckApplicationService;

//    @BeforeEach
//    public void preSetup(){
//        when(securityCheckApplicationService.isCustomer()).thenReturn(true);
//        when(securityCheckApplicationService.getAuthenticatedUserId()).thenReturn(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value());
//    }
}