package main.java.com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.jwt.GeradorJwt;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RepositorioUsuario repo;
    private final GeradorJwt geradorJwt;
    private final PasswordEncoder encoder;

    public AuthController(RepositorioUsuario repo, GeradorJwt geradorJwt, PasswordEncoder encoder) {
        this.repo = repo;
        this.geradorJwt = geradorJwt;
        this.encoder = encoder;
    }

    // Diagnóstico rápido
    @GetMapping("/ping")
    public String ping() { return "auth-ok"; }

    // Login via POST com query string
    @PostMapping("/login")
    public ResponseEntity<String> loginPost(@RequestParam String username, @RequestParam String password) {
        return autenticar(username, password);
    }

    // Login via GET (facilita teste)
    @GetMapping("/login")
    public ResponseEntity<String> loginGet(@RequestParam String username, @RequestParam String password) {
        return autenticar(username, password);
    }

    private ResponseEntity<String> autenticar(String username, String password) {
        Usuario u = repo.findByNomeUsuario(username).orElse(null);
        if (u == null || !encoder.matches(password, u.getSenha())) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
        return ResponseEntity.ok(geradorJwt.gerar(u.getNomeUsuario(), u.getPerfil().name()));
    }
}