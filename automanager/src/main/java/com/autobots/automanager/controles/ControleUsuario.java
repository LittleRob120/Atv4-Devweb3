package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.Perfil;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class ControleUsuario {
    private final RepositorioUsuario repo;

    public ControleUsuario(RepositorioUsuario repo) {
        this.repo = repo;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador','Gerente')")
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador','Gerente')")
    public Usuario obter(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador') or (hasRole('Gerente') and #usuario.perfil != T(com.autobots.automanager.modelos.Perfil).Administrador)")
    public Usuario criar(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Gerente')")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario dados) {
        Usuario u = repo.findById(id).orElseThrow();
        if (!podeGerenciar(dados.getPerfil())) {
            throw new RuntimeException("Gerente não pode definir perfil Administrador");
        }
        u.setNomeUsuario(dados.getNomeUsuario());
        u.setSenha(dados.getSenha());
        u.setPerfil(dados.getPerfil());
        return repo.save(u);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public void remover(@PathVariable Long id) {
        repo.deleteById(id);
    }

    private boolean podeGerenciar(Perfil perfil) {
        // Administrador pode tudo; Gerente não pode criar/alterar Administrador
        return perfil != Perfil.Administrador;
    }
}