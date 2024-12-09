package models;

public class Filmes {
    private int id;
    private String nome;
    private String genero;
    private String diretor;
    private String faixaEtaria;
    private String data;
    private String disponivel;

    // Construtores
    public Filmes() {
    }

    public Filmes (String nome, String genero, String diretor, String faixaEtaria, String data, String disponivel) {
        this.nome = nome;
        this.genero = genero;
        this.diretor = diretor;
        this.faixaEtaria = faixaEtaria;
        this.data = data;
        this.disponivel = disponivel;
    }

    public Filmes (int id, String nome, String genero, String diretor, String faixaEtaria, String data, String disponivel) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.diretor = diretor;
        this.faixaEtaria = faixaEtaria;
        this.data = data;
        this.disponivel = disponivel;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    // ID somente leitura, sem setter

    public String getNome() {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor (String diretor) {
        this.diretor = diretor;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria (String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }
    public String getData() {
        return data;
    }

    public void setData (String data) {
        this.data = data;
    }
    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel (String disponivel) {
        this.disponivel = disponivel;
    }

    // toString

    @Override
    public String toString() {
        return "Filmes [id=" + id + ", nome=" + nome + ", genero=" + genero + ", diretor=" + diretor + ", faixaEtaria=" + faixaEtaria + ", data=" + data + ", disponivel=" + disponivel + "]";
    }
}    