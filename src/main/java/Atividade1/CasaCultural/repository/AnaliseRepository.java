package Atividade1.CasaCultural.repository;

import Atividade1.CasaCultural.model.Analise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnaliseRepository extends JpaRepository<Analise, Long> {

    List<Analise> findByFilmeId(Long filmeId); // Usando a navegação de entidades
}