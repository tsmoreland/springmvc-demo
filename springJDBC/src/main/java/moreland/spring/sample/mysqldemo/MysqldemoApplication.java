package moreland.spring.sample.mysqldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class MysqldemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqldemoApplication.class, args);
	}

}
