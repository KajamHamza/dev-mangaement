package ma.ac.uir.devmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ma.ac.uir.devmanagement.entity")
public class DevManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevManagementApplication.class, args);
	}

}
