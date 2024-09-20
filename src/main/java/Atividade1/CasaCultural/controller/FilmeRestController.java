package Atividade1.CasaCultural.controller;

import Atividade1.CasaCultural.model.Filme;
import Atividade1.CasaCultural.model.Analise;
import Atividade1.CasaCultural.repository.FilmeRepository;
import Atividade1.CasaCultural.repository.AnaliseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmes")
public class FilmeRestController {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private AnaliseRepository analiseRepository;

    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> obterFilmePorId(@PathVariable Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Filme> criarFilme(@RequestBody Filme filme) {
        Filme novoFilme = filmeRepository.save(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFilme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable Long id, @RequestBody Filme filme) {
        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        filme.setId(id);
        Filme filmeAtualizado = filmeRepository.save(filme);
        return ResponseEntity.ok(filmeAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id) {
        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        filmeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/analises")
    public ResponseEntity<List<Analise>> listarAnalisesPorFilme(@PathVariable Long id) {
        List<Analise> analises = analiseRepository.findByFilmeId(id);
        return ResponseEntity.ok(analises);
    }

    @PostMapping("/{id}/analises")
    public ResponseEntity<Analise> criarAnalise(@PathVariable Long id, @RequestBody Analise analise) {
        Optional<Filme> filme = filmeRepository.findById(id);
        if (filme.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        analise.setFilme(filme.get());
        Analise novaAnalise = analiseRepository.save(analise);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAnalise);
    }

    @DeleteMapping("/analises/{id}")
    public ResponseEntity<Void> deletarAnalise(@PathVariable Long id) {
        if (!analiseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        analiseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/analises/{id}")
    public ResponseEntity<Analise> atualizarAnalise(@PathVariable Long id, @RequestBody Analise analise) {
        if (!analiseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        analise.setId(id);
        Analise analiseAtualizada = analiseRepository.save(analise);
        return ResponseEntity.ok(analiseAtualizada);
    }
}