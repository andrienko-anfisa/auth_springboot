package ru.andrienko.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.andrienko.springboot.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
//                Доступ только для не зарегистрированных пользователей
//                .antMatchers("/signIn").not().fullyAuthenticated()
                .antMatchers("/signUp").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
//                .antMatchers("/allUsers").hasRole("ADMIN")
                .antMatchers("/editUser").hasRole("ADMIN")
                .antMatchers("/deleteUser*").hasRole("ADMIN")
//                .antMatchers("/newUser").hasRole("ADMIN")

                //Доступ разрешен всем пользователей
                .antMatchers("/signIn").permitAll()
                //Все остальные страницы требуют аутентификации
                .antMatchers("/").authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/signIn").usernameParameter("login").passwordParameter("password")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
