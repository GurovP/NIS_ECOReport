package eco.nis.config;

import eco.nis.utilities.ConfigurationUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:persistence.properties"),
        @PropertySource("classpath:persistence-${spring.profiles.active}.properties")}) //if same key, this will 'win'
@EnableJpaRepositories(basePackages = "eco.nis.repositories")
public class PersistenceConfig {

    private final Environment env;

    public PersistenceConfig(Environment env) {

        super();
        this.env = env;
    }

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"eco.nis.models.entities"});
        sessionFactory.setHibernateProperties(ConfigurationUtility.getHibernateProperties(env));

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        String jdbcUrl = String.format("%s://%s:%s/%s", env.getProperty("jdbc.prefix"), env.getProperty("jdbc.servername"), env.getProperty("jdbc.serverport"), env.getProperty("jdbc.dbname"));
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
