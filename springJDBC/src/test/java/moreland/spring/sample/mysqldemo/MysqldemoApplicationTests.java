package moreland.spring.sample.mysqldemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import moreland.spring.sample.mysqldemo.configuration.DatabaseConfiguration;
import moreland.spring.sample.mysqldemo.configuration.SecurityConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfiguration.class, SecurityConfiguration.class})
class MysqldemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
