package eco.nis.utilities;

import org.springframework.core.env.Environment;

import java.util.Properties;

public final class ConfigurationUtility {

    public static Properties getHibernateProperties(Environment environment) {

        final Properties properties = new Properties();

        String showSql = environment.getProperty("hibernate.show_sql");
        properties.setProperty("hibernate.show_sql", showSql == null ? "false" : showSql);

        String hbn2ddlAuto = environment.getProperty("hibernate.hbm2ddl.auto");
        if (hbn2ddlAuto != null)
            properties.setProperty("hibernate.hbm2ddl.auto", hbn2ddlAuto);

        String hbnDialect = environment.getProperty("hibernate.dialect");
        if (hbnDialect != null)
            properties.setProperty("hibernate.dialect", hbnDialect);

        String hbnLevelCache = environment.getProperty("hibernate.cache.use_second_level_cache");
        if (hbnLevelCache != null)
            properties.setProperty("hibernate.cache.use_second_level_cache", hbnLevelCache);

        String hbnQueryCache = environment.getProperty("hibernate.cache.use_query_cache");
        if (hbnQueryCache != null)
            properties.setProperty("hibernate.cache.use_query_cache", hbnQueryCache);


        String batch_size = environment.getProperty("spring.jpa.properties.hibernate.jdbc.batch_size");
        if (batch_size != null)
            properties.setProperty("hibernate.jdbc.batch_size", batch_size);

        String order_inserts = environment.getProperty("spring.jpa.properties.hibernate.order_inserts");
        if (order_inserts != null)
            properties.setProperty("hibernate.order_inserts", order_inserts);

        String order_updates = environment.getProperty("spring.jpa.properties.hibernate.order_updates");
        if (order_updates != null)
            properties.setProperty("hibernate.order_updates", order_updates);

        String order_deletes = environment.getProperty("spring.jpa.properties.hibernate.order_deletes");
        if (order_deletes != null)
            properties.setProperty("hibernate.order_deletes", order_deletes);

        String batch_versioned_data = environment.getProperty("spring.jpa.properties.hibernate.batch_versioned_data");
        if (batch_versioned_data != null)
            properties.setProperty("hibernate.batch_versioned_data", batch_versioned_data);

        String db_structure_naming_strategy = environment.getProperty("spring.jpa.properties.hibernate.id.db_structure_naming_strategy");
        if (db_structure_naming_strategy != null)
            properties.setProperty("hibernate.id.db_structure_naming_strategy", db_structure_naming_strategy);


        //properties.setProperty("hibernate.globally_quoted_identifiers", "true");

        String registerEnversListeners = environment.getProperty("hibernate.envers.autoRegisterListeners");
        if (registerEnversListeners != null)
            properties.setProperty("hibernate.envers.autoRegisterListeners", registerEnversListeners);

        return properties;
    }
}
