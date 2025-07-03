package com.gabrielpanucci.agenda.meuprojeto.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 1. Marca a classe como uma fonte de configurações para o Spring
@EnableWebSecurity  // 2. Ativa a configuração de segurança web customizada do Spring
public class SecurityConfig {

        // 3. Define nossos próprios usuários, senhas e "cargos" (Roles) em memória
        @Bean
        public UserDetailsService userDetailsService() {
        // Cria um usuário chamado "user"
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}12345") // {noop} indica que a senha não está criptografada (apenas para desenvolvimento!)
                .roles("USER")  // Atribui o cargo/papel de USER
                .build();

        // Cria um usuário chamado "admin"
        UserDetails admin = User.builder()
            .username("admin")
            .password("{noop}admin123")
            .roles("ADMIN", "USER") // O admin tem os dois cargos
            .build();

        // Retorna um gerenciador que cuida desses usuários em memória
        return new InMemoryUserDetailsManager(user, admin);

    }

    // 4. Configura as regras de segurança para as requisições HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Regra para DELETAR: só quem tem o cargo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/materias/**").hasRole("ADMIN")

                        // 2. Regra para CRIAR e ATUALIZAR: precisa do cargo USER (admin também tem)
                        .requestMatchers(HttpMethod.POST, "/api/materias/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/materias/**").hasRole("USER")

                        // 3. Para QUALQUER outra requisição (ex: GETs), basta estar autenticado
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
