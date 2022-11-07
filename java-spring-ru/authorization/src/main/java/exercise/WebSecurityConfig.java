package exercise;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.DELETE;

import exercise.model.UserRole;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().and().sessionManagement().disable();
//        Допишите содержимое метода protected void configure(). Настройте авторизацию следующим образом:
//
//        Просматривать корневую страницу / и пройти регистрацию могут все не аутентифицированные пользователи
//        Просматривать список пользователей и информацию о конкретном пользователе могут все аутентифицированные
//        пользователи (пользователи с ролью "USER") и администраторы (роль "ADMIN")
//        Удалить пользователя может только администратор
//        Запустите приложение и попробуйте выполнять различные действия о имени пользователя и
//        администратора. Email пользователей и пароли можно посмотреть в файле scr/main/resources/import.sql
        // BEGIN
        http.csrf().disable().authorizeRequests().and().sessionManagement().disable();

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                // Регистрация доступна всем пользователям
                .antMatchers(POST, "/users").permitAll()
                // Доступ к url /hello доступен аутентифицированным пользователям
                // с полномочиями "USER" или "ADMIN"
                .antMatchers(GET,"/*").hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .antMatchers(DELETE, "/users/*").hasAuthority(UserRole.ADMIN.name())
                .and().httpBasic();
        // END
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}
