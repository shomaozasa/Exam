package ScoreManager.M.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
 
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private DataSource dataSource;
 
	@Autowired
	private UserDetailsService userService;
 
	@Bean
	public UserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager jdbcManager = new JdbcUserDetailsManager(this.dataSource);
		return jdbcManager;
	}
 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic(
						(basic) -> basic.disable())
				.authorizeHttpRequests(request -> {
					request
							.requestMatchers("/login").permitAll()// ログインページは全許可
							.requestMatchers("/webjars/**").permitAll() // webjarsのパスは全許可
							.requestMatchers("/js/**").permitAll()      // JSのstaticファイル
							.requestMatchers("/css/**").permitAll()     // CSSのstaticファイル
							.requestMatchers("/images/**").permitAll()  // 画像のstaticファイル
							.anyRequest().authenticated();              // それ以外は認証必須
				})
				.formLogin(form -> {
					form
							.loginPage("/login")             // ログインページのURI
							.loginProcessingUrl("/login")    // ログインを実施するページのURI
							.defaultSuccessUrl("/")           // ログイン完了後の遷移先
							.failureUrl("/login?error=true") // ログインエラーページのURI
							.usernameParameter("id") // ログインユーザのname属性
							.passwordParameter("password");   // ログインパスワードのname属性
				})
				.userDetailsService(this.userService)
				.logout(logout -> {
					logout
							.logoutUrl("/logout")
							.logoutSuccessUrl("/login")
							.deleteCookies("JSESSIONID")
							.invalidateHttpSession(true);
				});

		return http.build();
	}
}

