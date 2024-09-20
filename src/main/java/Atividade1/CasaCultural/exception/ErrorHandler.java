package Atividade1.CasaCultural.exception;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Component
public class ErrorHandler {

    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        e.printStackTrace(); // Imprime o stack trace para debug no console

        // Mensagem de erro mais amigável, se possível
        String errorMessage = "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.";
        if (e.getMessage() != null) {
            errorMessage = e.getMessage();
        }

        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/error"; // Página de erro genérica (crie esta página)
    }

    public String handleValidationErrors(Model model, BindingResult result) {
        // Coleta todas as mensagens de erro de validação
        String errorMessage = result.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("<br>"));

        model.addAttribute("errorMessage", errorMessage);
        return "CadastrarFilme"; // Retorna para o formulário com as mensagens de erro
    }
}