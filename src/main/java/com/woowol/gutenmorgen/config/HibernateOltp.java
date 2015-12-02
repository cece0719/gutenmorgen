package com.woowol.gutenmorgen.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@EnableTransactionManagement
public class HibernateOltp {
    public static final String db = "oltp";

    @Value("${database." + db + ".jdbc.driver}")
    private String driver;
    @Value("${database." + db + ".jdbc.url}")
    private String url;
    @Value("${database." + db + ".jdbc.username}")
    private String username;
    @Value("${database." + db + ".jdbc.password}")
    private String password;
    @Value("${database." + db + ".hibernate.dialect}")
    private String dialct;
    @Value("${database." + db + ".hibernate.show_query}")
    private String show_query;
    @Value("${database." + db + ".hibernate.format_sql}")
    private String format_sql;

    @SuppressWarnings("serial")
    @Bean(name = db + "SessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean() {{
            setPackagesToScan("com.woowol.gutenmorgen");
        }};
        sessionFactory.setDataSource(new DriverManagerDataSource() {{
            setDriverClassName(driver);
            setUrl(url);
            setUsername(username);
            setPassword(password);
        }});
        sessionFactory.setHibernateProperties(new Properties() {{
            put("hibernate.dialect", dialct);
            put("hibernate.show_sql", show_query);
            put("hibernate.format_sql", format_sql);
        }});
        sessionFactory.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
        return sessionFactory;
    }

    @SuppressWarnings("serial")
    @Bean(name = db + "TxManager")
    @Autowired
    public HibernateTransactionManager txManager(final SessionFactory sessionFactory) {
        return new HibernateTransactionManager() {{
            setSessionFactory(sessionFactory);
        }};
    }
}