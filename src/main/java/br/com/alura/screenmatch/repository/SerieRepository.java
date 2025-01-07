package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    //Buscas por trecho do título
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
}
