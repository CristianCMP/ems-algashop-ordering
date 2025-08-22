package com.algaworks.algashop.ordering.infrastructure.percistence;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class HibernateConfiguration {

//    Alternativa ao @AttributeOverride (Abordagem automática)
//    Ao invés de sobrescrever o mapeamento e configurar coluna por coluna, você pode alterar a estratégia utilizada pelo
//    Hibernate, e fazer com que ele considere o path dos objetos embutidos (Embeddable).

//    @Bean
//    public ImplicitNamingStrategy implicit() {
//        return new ImplicitNamingStrategyComponentPathImpl();
//    }
}