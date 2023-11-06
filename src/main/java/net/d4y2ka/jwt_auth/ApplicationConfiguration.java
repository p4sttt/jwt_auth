package net.d4y2ka.jwt_auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class ApplicationConfiguration {
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .findAndRegisterModules();
  }

}
