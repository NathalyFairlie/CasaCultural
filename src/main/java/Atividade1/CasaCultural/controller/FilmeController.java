package Atividade1.CasaCultural.controller;

import Atividade1.CasaCultural.model.Filme;
import Atividade1.CasaCultural.model.Analise;
import Atividade1.CasaCultural.repository.FilmeRepository;
import Atividade1.CasaCultural.repository.AnaliseRepository;
import Atividade1.CasaCultural.exception.ErrorHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private AnaliseRepository analiseRepository;

    @Autowired
    private ErrorHandler errorHandler;

    // 1. Listar todos os filmes
    @GetMapping("/filmes")
    public String listarFilmes(Model model) {
        List<Filme> filmes = filmeRepository.findAll();
        model.addAttribute("filmes", filmes);
        return "ListarFilmes";
    }

    // 2. Exibir formulário para cadastrar um novo filme
    @GetMapping("/filmes/novo")
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "CadastrarFilme";
    }

    // 3. Processar o cadastro do novo filme
    @PostMapping("/filmes/novo")
    public String cadastrarFilme(@Valid @ModelAttribute Filme filme,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Erro no cadastro: Verifique os campos obrigatórios e corrija os erros.");
            return "CadastrarFilme";
        }

        try {
            filmeRepository.save(filme);
            redirectAttributes.addFlashAttribute("successMessage", "Filme cadastrado com sucesso!");
            return "redirect:/filmes";

        } catch (Exception e) {
            return errorHandler.handleException(e, redirectAttributes);
        }
    }

    // 4. Exibir detalhes de um filme específico
    @GetMapping("/filmes/{id}")
    public String detalhesFilme(@PathVariable Long id, Model model) {
        try {
            Filme filme = filmeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

            List<Analise> analises = analiseRepository.findByFilmeId(filme.getId());

            model.addAttribute("filme", filme);
            model.addAttribute("analises", analises);
            model.addAttribute("novaAnalise", new Analise());

            return "DetalhesFilme";

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    // 5. Exibir formulário para editar um filme
    @GetMapping("/filmes/editar/{id}")
    public String exibirFormularioEditarFilme(@PathVariable Long id, Model model) {
        try {
            Filme filme = filmeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado com ID: " + id));
            model.addAttribute("filme", filme);
            return "EditarFilme";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    // 6. Processar a edição de um filme
    @PostMapping("/filmes/editar/{id}")
    public String editarFilme(@PathVariable Long id,
                              @Valid Filme filme,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "EditarFilme";
        }

        try {
            Filme filmeExistente = filmeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado com ID: " + id));

            filmeExistente.setTitulo(filme.getTitulo());
            filmeExistente.setSinopse(filme.getSinopse());
            filmeExistente.setGenero(filme.getGenero());
            filmeExistente.setAnoLancamento(filme.getAnoLancamento());

            filmeRepository.save(filmeExistente);

            redirectAttributes.addFlashAttribute("successMessage", "Filme atualizado com sucesso!");
            return "redirect:/filmes/" + id;

        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/filmes";
        }
    }

    // 7. Remover um filme
    @GetMapping("/filmes/deletar/{id}")
    public String deletarFilme(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            filmeRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Filme removido com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao remover o filme: " + e.getMessage());
        }
        return "redirect:/filmes";
    }

    // 8. Adicionar uma análise a um filme
    @PostMapping("/filmes/{id}/analises")
    public String adicionarAnalise(@PathVariable Long id,
                                   @Valid @ModelAttribute("novaAnalise") Analise novaAnalise,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro na análise: " + result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/filmes/" + id;
        }

        try {
            Filme filme = filmeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

            novaAnalise.setFilme(filme);
            analiseRepository.save(novaAnalise);

            filme.getAnalises().add(novaAnalise);
            filmeRepository.save(filme);

            redirectAttributes.addFlashAttribute("novaAnalise", new Analise());
            redirectAttributes.addFlashAttribute("successMessage", "Análise adicionada com sucesso!");
            return "redirect:/filmes/" + id;

        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/filmes";
        }
    }

}