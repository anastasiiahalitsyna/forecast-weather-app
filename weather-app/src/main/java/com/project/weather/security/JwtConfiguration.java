package com.project.weather.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration(proxyBeanMethods = false)
public class JwtConfiguration {

    @Bean
    public JwtEncoder jwtEncoder(JwtProperties jwtProperties) {
        final var jwk = new RSAKey.Builder(jwtProperties.getPublicKey())
                .privateKey(jwtProperties.getPrivateKey())
                .build();
        return new NimbusJwtEncoder(
                new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

    @Bean
    public JwtDecoder jwtDecoder(JwtProperties jwtProperties) {
        return NimbusJwtDecoder
                .withPublicKey(jwtProperties.getPublicKey())
                .build();
    }

    @Bean
    public JwtService jwtService(JwtProperties jwtProperties, final JwtEncoder jwtEncoder) {
        return new JwtService(jwtProperties.getIssuer(), jwtProperties.getTtl(), jwtEncoder);
    }
}
