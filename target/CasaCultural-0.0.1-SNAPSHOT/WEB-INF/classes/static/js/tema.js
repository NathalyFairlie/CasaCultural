$(document).ready(function() {
    // Função para obter o valor do cookie
    function getCookie(name) {
        let value = "; " + document.cookie;
        let parts = value.split("; " + name + "=");
        if (parts.length === 2) return parts.pop().split(";").shift();
    }

    // Função para definir o cookie
    function setCookie(name, value, days) {
        let expires = "";
        if (days) {
            let date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }

    // Aplicar o tema ao carregar a página
    let tema = getCookie('tema') || 'claro'; // Default para 'claro' se não houver cookie
    if (tema === 'escuro') {
        $('body').addClass('dark-mode');
        $('#temaBtn').text('Tema Claro');
        $('#theme-stylesheet').attr('href', '/css/DarkMode.css');
    } else {
        $('body').removeClass('dark-mode');
        $('#temaBtn').text('Tema Escuro');
        $('#theme-stylesheet').attr('href', '/css/style.css');
    }

    // Alternar tema ao clicar no botão
    $('#temaBtn').on('click', function() {
        let temaAtual = $('body').hasClass('dark-mode') ? 'escuro' : 'claro';
        if (temaAtual === 'escuro') {
            $('body').removeClass('dark-mode');
            setCookie('tema', 'claro', 7);
            $(this).text('Tema Escuro');
            $('#theme-stylesheet').attr('href', '/css/style.css');
        } else {
            $('body').addClass('dark-mode');
            setCookie('tema', 'escuro', 7);
            $(this).text('Tema Claro');
            $('#theme-stylesheet').attr('href', '/css/DarkMode.css');
        }
    });
});
