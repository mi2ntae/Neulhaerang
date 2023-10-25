package com.finale.neulhaerang.global.config;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	public static final String SECURITY_SCHEMA_NAME = "Authorization";
	public static final String AUTHORIZATION_SCOPE_GLOBAL = "global";
	public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC = "accessEverything";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
			// .securityContexts(newArrayList(this.securityContext()))
			// .securitySchemes(newArrayList(this.apiKey()))
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.ant("/**"))
			.build();
	}

	private ApiKey apiKey() {
		return new ApiKey(SECURITY_SCHEMA_NAME, "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL, AUTHORIZATION_SCOPE_GLOBAL_DESC);
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(new SecurityReference(SECURITY_SCHEMA_NAME, authorizationScopes));
	}
}