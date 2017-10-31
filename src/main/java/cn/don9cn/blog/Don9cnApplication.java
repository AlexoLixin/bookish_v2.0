package cn.don9cn.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages = { "cn.don9cn.blog" })
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Don9cnApplication /*extends SpringBootServletInitializer*/ {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Don9cnApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(Don9cnApplication.class, args);
	}

}
