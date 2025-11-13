package br.com.cardappio.config;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        List<String> origins = corsProperties.getAllowedOriginsList();

        registry.addMapping("/**")
                .allowedOrigins(origins.isEmpty() ? new String[]{} : origins.toArray(new String[0]))
                .allowedMethods(corsProperties.getAllowedMethods() != null ?
                        corsProperties.getAllowedMethods().toArray(new String[0]) : new String[]{})
                .allowedHeaders(corsProperties.getAllowedHeaders() != null ?
                        corsProperties.getAllowedHeaders().toArray(new String[0]) : new String[]{})
                .allowCredentials(true)
                .maxAge(3600);
    }
}