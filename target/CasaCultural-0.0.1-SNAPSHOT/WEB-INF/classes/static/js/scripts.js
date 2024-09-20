$(document).ready(function() {

  // Função para obter o token CSRF do cabeçalho
  function getCsrfToken() {
    var csrfToken = null;
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
      var cookie = cookies[i].trim();
      if (cookie.startsWith('X-CSRF-TOKEN=')) {
        csrfToken = decodeURIComponent(cookie.substring('X-CSRF-TOKEN='.length, cookie.length));
        break;
      }
    }
    return csrfToken;
  }

  $('#tabelaFilmes').on('click', '.deletar-filme', function(event) {
    event.preventDefault();
    var filmeId = $(this).data('filme-id');
    var csrfToken = getCsrfToken();

    if (confirm('Tem certeza de que deseja deletar este filme?')) {
      $.ajax({
        url: `/api/filmes/${filmeId}`,
        type: 'DELETE',
        headers: {
          'X-CSRF-TOKEN': csrfToken 
        },
        success: function() {
          $('#filme-' + filmeId).remove();
          alert('Filme deletado com sucesso!'); 
        },
        error: function(error) {
          alert('Erro ao deletar o filme. Detalhes: ' + error.responseText);
          console.error("Erro na requisição AJAX:", error); 
        }
      });
    }
  });
});
