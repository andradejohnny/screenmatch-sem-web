package br.com.alura.screenmatch;


import br.com.alura.screenmatch.principal.Principal;
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
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
