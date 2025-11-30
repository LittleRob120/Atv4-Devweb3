package com.autobots.automanager.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class ProvedorJwt {
    private final GeradorJwt gerador;
    private AnalisadorJwt analisador;
    private ValidadorJwt validador;

    public ProvedorJwt(GeradorJwt gerador) {
        this.gerador = gerador;
    }

    // Gera JWT assumindo papel padrão "Cliente" para compatibilidade
    public String proverJwt(String nomeUsuario) {
        return gerador.gerar(nomeUsuario, "Cliente");
    }

    public boolean validarJwt(String jwt) {
        analisador = new AnalisadorJwt(gerador.getSegredo(), jwt);
        validador = new ValidadorJwt();
        return validador.validar(analisador.obterReivindicacoes());
    }

    public String obterNomeUsuario(String jwt) {
        analisador = new AnalisadorJwt(gerador.getSegredo(), jwt);
        Claims reivindicacoes = analisador.obterReivindicacoes();
        return analisador.obterNomeUsuairo(reivindicacoes);
    }

    // Alias legado
    public String gerarJwt(String username) {
        return gerador.gerar(username, "Cliente");
    }
}