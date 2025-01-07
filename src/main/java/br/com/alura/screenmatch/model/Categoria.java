package br.com.alura.screenmatch.model;

//enum é usado para definir um conjunto de valores fixos e limitados, que representam uma lista de opções ou alternativas.

public enum Categoria {

    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String catergoriaPortugues;


    Categoria(String categoriaOmdb, String catergoriaPortugues)
    {
        this.categoriaOmdb = categoriaOmdb;
        this.catergoriaPortugues = catergoriaPortugues;
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

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.catergoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
