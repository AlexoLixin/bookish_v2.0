package cn.don9cn.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(scanBasePackages = { "cn.don9cn.blog" })
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Don9cnApplication {

	public static void main(String[] args) {
		SpringApplication.run(Don9cnApplication.class, args);
	}

}
