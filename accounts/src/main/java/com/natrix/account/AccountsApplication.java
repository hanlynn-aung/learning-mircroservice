package com.natrix.account;

import com.natrix.account.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*@ComponentScans({ @ComponentScan("com.natrix.account.controller") })
@EnableJpaRepositories("com.natrix.account.repository")
@EntityScan("com.natrix.account.entity")*/
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(AccountsContactInfoDto.class)
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservice REST API Documentation",
                description = "EazyBank Accounts microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Natrix",
                        email = "hanlynnaung1997@gmail.com",
                        url = "https://hanlynnaung-portfolio-ky6ft6bnj-han-lynn-aung.vercel.app/"
                )
//                license = @License(
//                        name = "Apache 2.0",
//                        url = "https://hanlynnaung-portfolio-ky6ft6bnj-han-lynn-aung.vercel.app/" //dummy
//                )
//        ),
//        externalDocs = @ExternalDocumentation(
//                description = "EazyBank Accounts microservice REST API Documentation",
//                url = "https://hanlynnaung-portfolio-ky6ft6bnj-han-lynn-aung.vercel.app/" //dummy
        )
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
