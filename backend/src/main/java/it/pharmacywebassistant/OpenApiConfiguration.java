package it.pharmacywebassistant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfiguration {

    // http://localhost:8080/swagger-ui/index.html#/

    @Bean
    OpenAPI getCustomOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Progetto Pharmacy-Web-Assistant")
                        .description("REST API in Spring Boot per la gestione di una farmacia")
                        .contact(new Contact().email("edo.oranger@email.com").name("Edoardo Oranger").url("https://github.com/mrOranger/"))
                        .license(new License().name("GNU General Public License v3.0").url("https://www.gnu.org/licenses/gpl-3.0.en.html"))
                        .version("1.0"));
    }

}