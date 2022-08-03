package com.service.rest.config;

import com.service.rest.controller.UserToXMLClient;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class UserToXMLClientConfig {
    @Value("${client.config.context.path}")
    private String CONTEXT_PATH = "";

    @Value("${soap.uri}")
    private String SOAP_URI = "";

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(CONTEXT_PATH);
        return marshaller;
    }

    @Bean
    public UserToXMLClient userToXMLClient(Jaxb2Marshaller marshaller) {
        UserToXMLClient client = new UserToXMLClient();
        client.setDefaultUri(SOAP_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

//    @Bean
//    public static SessionFactory getCurrentSession() {
//        // Hibernate 5.4 SessionFactory example without XML
//        Map<String, String> settings = new HashMap<>();
//        settings.put("connection.driver_class", "org.postgresql.Driver");
//        settings.put("dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        settings.put("hibernate.connection.url", "jdbc:postgresql://localhost/jsontoxml");
//        settings.put("hibernate.connection.username", "user1");
//        settings.put("hibernate.connection.password", "1232");
//        settings.put("hibernate.current_session_context_class", "thread");
//        //settings.put("hibernate.show_sql", "true");
//        //settings.put("hibernate.format_sql", "true");
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(settings).build();
//
//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        Metadata metadata = metadataSources.buildMetadata();
//
//        return metadata.getSessionFactoryBuilder().build();
//    }

    @Bean
    public SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources.buildMetadata();

        return metadata.getSessionFactoryBuilder().build();
    }

}
