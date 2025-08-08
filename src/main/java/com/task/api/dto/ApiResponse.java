package com.task.api.dto;

import java.time.LocalDateTime;

// Classe responsável por criar uma resposta padrão à todas as consultas à API
public class ApiResponse<T> {

    private final LocalDateTime timestamp;
    private final boolean sucesso;
    private final String mensagem;
    private final String info;
    private final T dados;

    public ApiResponse(boolean sucesso, String mensagem, T dados) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
        this.info = "[INFO] Aluno: Gabriel Mateus Pereira Ribeiro - RU: 4585408";
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public T getDados() {
        return dados;
    }

    public String getInfo() {
        return info;
    }
}