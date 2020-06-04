
package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	private static final String ADMIN = "admin";
	
	private static final String PROFESSIONAL = "professional";
	
	private static final String CLIENT = "client";


	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
			.antMatchers(HttpMethod.GET, "/", "/oups").permitAll()
			.antMatchers("/clients/**").permitAll()
			.antMatchers("/admin/**").hasAnyAuthority(ADMIN)
			.antMatchers("/owners/**").hasAnyAuthority("owner", ADMIN)
			.antMatchers("/professionals/filter").permitAll()
			.antMatchers("/professionals").permitAll()
			.antMatchers("/professionals/find").permitAll()
			.antMatchers("/professionals/**").hasAnyAuthority(PROFESSIONAL, ADMIN)
			.antMatchers("/appointments/pro").hasAnyAuthority(PROFESSIONAL)
			.antMatchers("/appointments/*/consultation").hasAnyAuthority(PROFESSIONAL)
			.antMatchers("/appointments/*/details").hasAnyAuthority(CLIENT)
			.antMatchers("/appointments/*/absent").hasAnyAuthority(PROFESSIONAL)
			.antMatchers("/appointments/**").hasAnyAuthority(CLIENT)
			.antMatchers("/payments/**").permitAll()
			.antMatchers("/vets/**")
			.authenticated().anyRequest().denyAll()
			.and()
				.formLogin()
				.loginProcessingUrl("/signin")
				.loginPage("/login").permitAll()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login");

		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource).usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
			.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?").passwordEncoder(this.passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
		return encoder;
	}

}
