package eco.nis;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Locale;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"eco.nis"})
public class NIS_ECOReportApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NIS_ECOReportApplication.class, args);
	}

	public static final Logger logger = LoggerFactory.getLogger(NIS_ECOReportApplication.class);

	@PostConstruct
	public void setDefaultLocale() {
		// Set the default locale to one that uses a comma as a decimal separator (e.g., US English)
		Locale.setDefault(Locale.US);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NIS_ECOReportApplication.class);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Autowired
	private Environment env;

	@PersistenceContext
	private EntityManager entityManager;

	@Qualifier("dataSource")
	@Autowired
	public DataSource dataSource;
}