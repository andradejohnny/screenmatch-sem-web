package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
    //instancia o conversor
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY= "&apikey=ece053e5";

    public void exibeMenu()
    {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();

        //Consome os dados da API e obtem os dados da série e atribui na var json
        var json = consumoApi.obterDados( ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        //Transforma para dados série
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++)
		{
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);

			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);

		}
		temporadas.forEach(System.out::println);

        //Criando lista com Titulos dos episodios de cada temporada
//        for (int i = 0; i<dados.totalTemporadas(); i++)
//        {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j<episodiosTemporada.size(); j++)
//            {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        //Refazendo de forma mais simples o processo anterior utilizando lambda
        // temporadas: Lista de objetos 'DadosTemporada' que será iterada.
        // forEach: Método para executar uma ação em cada elemento da lista.
        // t: Representa cada temporada ('DadosTemporada') durante a iteração.
        // t.episodios(): Retorna a lista de episódios da temporada atual ('t').
        // e: Representa cada episódio ('DadosEpisodio') durante a iteração.
        // e.titulo(): Retorna o título do episódio atual ('e').
        // System.out.println: Imprime o título do episódio no console.


        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        temporadas.forEach(System.out::println);


        //Stream organização de um fluxo de dados com operações encadeadas
//        List<String> nomes = Arrays.asList("Johnny", "Julya", "Manu", "Elizabete", "Johny");
//
//        nomes.stream()
//                //ordenar em ordem alfabética
//                .sorted()
//                //Limita em 3 nomes
//                .limit(3)
//                //Pega nomes com letra J
//                .filter(n -> n.startsWith("J"))
//                //Altera esse nome para letra maiuscula
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        //dadosEpisodios.add(new DadosEpisodio("teste", 3, "10", "2020-10-10"));
        System.out.println("\nTop 5 episódios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);


    }
}
