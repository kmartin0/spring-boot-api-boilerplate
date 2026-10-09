package nl.kmartin.springbootapiboilerplate.security.authorizationserver;

import nl.kmartin.springbootapiboilerplate.utils.Endpoints;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;

@Configuration
class JwkSetEndpointConfiguration extends AuthorizationServerSecurityConfiguration {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http
				.requestMatchers()
				.mvcMatchers(Endpoints.JWKS)
				.and()
				.authorizeRequests()
				.mvcMatchers(Endpoints.JWKS).permitAll();
	}
}
