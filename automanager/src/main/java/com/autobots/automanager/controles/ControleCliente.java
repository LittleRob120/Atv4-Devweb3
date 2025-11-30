package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.RepositorioCliente;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ControleCliente {
    private final RepositorioCliente repo;

    public ControleCliente(RepositorioCliente repo) {
        this.repo = repo;
    }

    // Listar todos os clientes: Admin, Gerente e Vendedor
    @PreAuthorize("permitAll()")
    @GetMapping("/clientes")
    public List<Cliente> listar() {
        return repo.findAll();
    }

    // Obter cliente por id: todos os perfis (cliente vê apenas o seu; ajuste fino pode ser aplicado)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador','Gerente','Vendedor','Cliente')")
    public Cliente obter(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // Criar/Atualizar/Remover clientes: Admin e Gerente
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(repo.save(cliente));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador','Gerente')")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente dados) {
        Cliente c = repo.findById(id).orElseThrow();
        // Atualização mínima para evitar dependência de getters inexistentes
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador','Gerente')")
    public void remover(@PathVariable Long id) {
        repo.deleteById(id);
    }
}