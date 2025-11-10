package br.com.cardappio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(Optional.ofNullable(corsProperties.getAllowedOrigins()).orElse(List.of()).toArray(new String[0]))
                .allowedMethods(Optional.ofNullable(corsProperties.getAllowedMethods()).orElse(List.of()).toArray(new String[0]))
                .allowedHeaders(Optional.ofNullable(corsProperties.getAllowedHeaders()).orElse(List.of()).toArray(new String[0]))
                .allowCredentials(true);
    }

}
