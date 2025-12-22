package com.natrix.card;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
/*@ComponentScans({ @ComponentScan("com.natrix.cards.controller") })
@EnableJpaRepositories("com.natrix.cards.repository")
@EntityScan("com.natrix.cards.entity")*/
@OpenAPIDefinition(
        info = @Info(
                title = "Cards microservice REST API Documentation",
                description = "EazyBank Cards microservice REST API Documentation",
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
public class CardApplication {

    public static void main(String[] args) {

        SpringApplication.run(CardApplication.class, args);
    }

}
