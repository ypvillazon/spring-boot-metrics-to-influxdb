package cu.ypvillazon.springboot.metricstoinfluxdb.service2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@EnableDiscoveryClient
public class Run {

	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
	}



}
