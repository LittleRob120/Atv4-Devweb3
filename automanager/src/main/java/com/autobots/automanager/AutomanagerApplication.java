package com.autobots.automanager;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.Perfil;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class AutomanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomanagerApplication.class, args);
    }

    // Garante um PasswordEncoder disponível
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner seed(RepositorioUsuario repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findAll().isEmpty()) {
                repo.save(novo("admin", Perfil.Administrador, encoder));
                repo.save(novo("gerente", Perfil.Gerente, encoder));
                repo.save(novo("vendedor", Perfil.Vendedor, encoder));
                repo.save(novo("cliente", Perfil.Cliente, encoder));
            }
        };
    }

    private Usuario novo(String username, Perfil perfil, PasswordEncoder encoder) {
        Usuario u = new Usuario();
        u.setNomeUsuario(username);
        u.setSenha(encoder.encode(username)); // senha = username
        u.setPerfil(perfil);
        return u;
    }

    @Bean
    public CommandLineRunner logMappings(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping rm) {
        return args -> {
            // opcional: listar mapeamentos; comente se não precisar
            // rm.getHandlerMethods().forEach((info, method) -> System.out.println(info + " -> " + method));
        };
    }

    @Bean
    CommandLineRunner seedAdmin(com.autobots.automanager.repositorios.RepositorioUsuario repo) {
        return args -> {
            boolean exists = repo.findAll().stream()
                .anyMatch(u -> "admin".equalsIgnoreCase(u.getNomeUsuario()));
            if (!exists) {
                var u = new com.autobots.automanager.entidades.Usuario();
                u.setNomeUsuario("admin");
                u.setSenha("admin");
                try {
                    var perfil = com.autobots.automanager.modelos.Perfil.valueOf("ADMIN");
                    u.setPerfil(perfil);
                } catch (IllegalArgumentException e) {
                    var all = com.autobots.automanager.modelos.Perfil.values();
                    if (all.length > 0) u.setPerfil(all[0]);
                }
                repo.save(u);
                System.out.println("Seeded user: admin/admin");
            }
        };
    }
}