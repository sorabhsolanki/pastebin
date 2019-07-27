package com.pastebin.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.pastebin.repository")
@PropertySource("classpath:db.properties")
public class PersistenceContext {

    private static final String[] ENTITY_PACKAGES = {
            "com.pastebin.entity"
    };

    private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "jdbc.driver";
    private static final String PROPERTY_NAME_DB_USER = "jdbc.username";
    private static final String PROPERTY_NAME_DB_PASSWORD = "jdbc.password";
    private static final String PROPERTY_NAME_DB_URL = "jdbc.url";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    private final Environment env;

    @Autowired
    public PersistenceContext(Environment env) {
        this.env = env;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource(Environment env) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DB_URL));
        dataSourceConfig.setUsername(env.getRequiredProperty(PROPERTY_NAME_DB_USER));
        dataSourceConfig.setPassword(env.getRequiredProperty(PROPERTY_NAME_DB_PASSWORD));
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(ENTITY_PACKAGES);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        hibernateProperties.put("hibernate.show_sql", env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        hibernateProperties.put("hibernate.format_sql", env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        em.setJpaProperties(hibernateProperties);
        return em;
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
