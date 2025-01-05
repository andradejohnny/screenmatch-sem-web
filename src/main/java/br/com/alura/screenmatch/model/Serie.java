package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaChatGPT;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

// Nova classe Serie para representar os dados de uma série, pois a classe DadosSerie não atendia a todas as necessidades,
// responsável por receber os dados brutos da API OMDB e transformá-los em um objeto com as informações tratadas e formatadas conforme a necessidade da aplicação.


public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    //avaliacao foi alterado de String para Double.
    private Double avaliacao;
    //genero foi alterado de String para um enum Categoria.
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;



    public Serie(DadosSerie dadosSerie)
    {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        // usamos OptionalDouble para converter de String para Double e definir um valor padrão de 0 caso a conversão falhe.
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        // usamos o método fromString() do enum Categoria para obter a categoria correta a partir da string retornada pela API.
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        // utiliza a API do ChatGPT para traduzir a sinopse
        // utiliza o metodo trim para não ter nenhum caracter em branco ou quebra de linha
        this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return
                "Genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", atores='" + atores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'';
    }
}
