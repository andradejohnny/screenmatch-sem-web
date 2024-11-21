package br.com.alura.screenmatch.service;

public interface IConvertesDados {
    <T> T obterDados(String json, Class<T> classe);
}
