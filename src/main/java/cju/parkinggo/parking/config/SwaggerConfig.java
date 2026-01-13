package cju.parkinggo.parking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("ParkingGo API")
                .description("ParkingGo API Documentation")
                .version("v1.0");

        return new OpenAPI()
                .info(info)
                .openapi("3.0.0"); // OpenAPI 버전 명시적으로 설정
    }
}