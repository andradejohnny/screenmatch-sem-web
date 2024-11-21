package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// Implementa CommandLineRunner para fazer com que a aplicação
// seja executada como uma aplicação de linha de comando, sem uma
// interface web.
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//O método run serve para permitir que você execute lógica
	// de negócio diretamente no terminal, sem a necessidade de
	// uma interface gráfica
	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();
		//Consome os dados da API e obtem os dados da série e atribiu na var json
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=ece053e5");
		// imprime a var json
		System.out.println(json);
		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);

		//instancia o conversor
		ConverteDados conversor = new ConverteDados();
		//Transforma para dados série
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

	}
}
