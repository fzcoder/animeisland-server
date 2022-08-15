package com.fzcoder.opensource.animeisland.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.c3p0.minPoolSize}")
    private int minPoolSize;
    @Value("${spring.datasource.c3p0.maxPoolSize}")
    private int maxPoolSize;
    @Value("${spring.datasource.c3p0.maxIdleTime}")
    private int maxIdleTime;
    @Value("${spring.datasource.c3p0.acquireIncrement}")
    private int acquireIncrement;
    @Value("${spring.datasource.c3p0.maxStatements}")
    private int maxStatements;
    @Value("${spring.datasource.c3p0.initialPoolSize}")
    private int initialPoolSize;
    @Value("${spring.datasource.c3p0.idleConnectionTestPeriod}")
    private int idleConnectionTestPeriod;
    @Value("${spring.datasource.c3p0.acquireRetryAttempts}")
    private int acquireRetryAttempts;
    @Value("${spring.datasource.c3p0.breakAfterAcquireFailure}")
    private boolean breakAfterAcquireFailure;
    @Value("${spring.datasource.c3p0.testConnectionOnCheckout}")
    private boolean testConnectionOnCheckout;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setMaxStatements(maxStatements);
        dataSource.setInitialPoolSize(initialPoolSize);
        dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
        dataSource.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
        dataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
        return dataSource;
    }
}
