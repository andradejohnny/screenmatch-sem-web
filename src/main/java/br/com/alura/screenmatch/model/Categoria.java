package br.com.alura.screenmatch.model;

//enum é usado para definir um conjunto de valores fixos e limitados, que representam uma lista de opções ou alternativas.

public enum Categoria {

    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb)
    {
        this.categoriaOmdb = categoriaOmdb;
    }

    //Método estático fromString() no enum para fazer essa conversão dinâmica.
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
