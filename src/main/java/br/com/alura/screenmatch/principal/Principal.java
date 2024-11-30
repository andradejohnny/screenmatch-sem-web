package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
//        System.out.println("\nTop 10 episódios: ");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A): " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação: " + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limitando em 10: " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeando: " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do titulo do episódio: ");
//        var trechoTitulo = leitura.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        }else
//        {
//            System.out.println("Episódio não encontrado!");
//        }
//

//
//
//        System.out.println("A partir de que ano você deseja ver os espisódios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        //Faz o LocalDate a partir do dia 1/01 do ano inserido pelo usuário
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd//MM/yyyy");
//        episodios.stream()
//                //Pega os episodios que a data de lançamento é depois que a data inserida
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                "  Episódio: " + e.getTitulo() +
//                                "  Data lançamento: " + e.getDataLancamento().format(formatador)
//                ));

            Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                    .filter(e -> e.getAvaliacao() > 0.0)
                    .collect(Collectors.groupingBy(Episodio::getTemporada,
                            Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor ep.: " + est.getMax());
        System.out.println("Pior ep.: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());


    }
}
