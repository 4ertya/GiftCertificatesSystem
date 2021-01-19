package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig implements WebMvcConfigurer {


//    private final ResourceLoader resourceLoader;
//
//    public DBConfig(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }

    //    @PostConstruct
//    protected void initialize(){
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(resourceLoader.getResource("classpath:/create_db.sql"));
//        populator.addScript(resourceLoader.getResource("classpath:/insert.sql"));
//        populator.setContinueOnError(false);
//        DatabasePopulatorUtils.execute(populator , dataSource());
//    }
    @Bean
    public TransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig("/db.properties");
        HikariDataSource dataSource;
        dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }


}
