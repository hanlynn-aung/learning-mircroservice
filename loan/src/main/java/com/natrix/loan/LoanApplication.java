package com.natrix.loan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
/*@ComponentScans({ @ComponentScan("com.natrix.loan.controller") })
@EnableJpaRepositories("com.natrix.loan.repository")
@EntityScan("com.natrix.loan.entity")*/
@OpenAPIDefinition(
        info = @Info(
                title = "Loan microservice REST API Documentation",
                description = "EazyBank Loan microservice REST API Documentation",
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
public class LoanApplication {

    public static void main(String[] args) {

        SpringApplication.run(LoanApplication.class, args);
    }

}
