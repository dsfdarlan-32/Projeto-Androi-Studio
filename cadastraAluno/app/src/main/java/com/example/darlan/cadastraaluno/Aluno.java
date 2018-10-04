package com.example.darlan.cadastraaluno;


public class Aluno {

   private int codigo;
   private String nome, email;

    public Aluno (){

    }

    public  Aluno (int cod , String _nome, String e_mail){
        this.codigo = cod;
        this.nome = _nome;
        this.email = e_mail;
    }

    public  Aluno ( String _nome, String e_mail){
        this.nome = _nome;
        this.email = e_mail;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
