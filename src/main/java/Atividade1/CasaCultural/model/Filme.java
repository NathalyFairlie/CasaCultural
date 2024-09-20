package Atividade1.CasaCultural.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 2, message = "O título deve conter pelo menos 2 caracteres.")
    private String titulo;

    @NotBlank(message = "A sinopse é obrigatória.")
    @Size(min = 5, message = "A sinopse deve conter pelo menos uma frase.")
    private String sinopse;

    @NotBlank(message = "O gênero é obrigatório.")
    @Size(min = 2, message = "O gênero deve conter pelo menos 2 caracteres.")
    private String genero;

    @Min(value = 1895, message = "O ano deve ser maior ou igual a 1895.")
    @Max(value = 9999, message = "O ano deve ter no máximo 4 dígitos.")
    private int anoLancamento;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Analise> analises = new ArrayList<>(); // Inicialize a lista
    
    public List<Analise> getAnalises() {
        return analises;
    }

    public void setAnalises(List<Analise> analises) {
        this.analises = analises;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }
    
    
}
