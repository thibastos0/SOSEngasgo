function Autenticar(event) {
      event.preventDefault();
      const email = document.getElementById("username").value;
      const senha = document.getElementById("password").value;
      const erro = document.getElementById("erro");
      
      // Loading state
      const button = event.target.querySelector('button[type="submit"]');
      const originalText = button.innerHTML;
      button.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Entrando...';
      button.disabled = true;

      // Login customizado via JSON (POST /autenticacao)
      // CSRF não é necessário aqui pois /autenticacao/** está na lista de ignore do WebConfig
      fetch("/autenticacao", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: 'same-origin', // ← CRÍTICO: aceita/envia cookies de sessão
        // Estrutura esperada pelo LoginController
        body: JSON.stringify({ 
          contatoUsuario: { email: email }, 
          senha: senha 
        })
      })
      .then(res => {
        if (res.ok) {
          // Autenticação bem-sucedida
          erro.innerHTML = '<i class="bi bi-check-circle-fill me-2"></i>Login realizado com sucesso!';
          erro.className = 'text-success small';
          // Redirecionar para página de emergência (SecurityContext já foi setado pelo backend)
          setTimeout(() => {
            window.location.href = '/emergencia';
          }, 500);
        } else {
          // Falha na autenticação (401 ou outro erro)
          return res.text().then(texto => {
            erro.innerHTML = '<i class="bi bi-exclamation-triangle-fill me-2"></i>' + (texto || 'Usuário ou senha inválidos');
            erro.className = 'text-danger small';
          });
        }
      })
      .catch(err => {
        erro.innerHTML = '<i class="bi bi-wifi-off me-2"></i>Erro de conexão. Verifique sua rede.';
        erro.className = 'text-danger small';
        console.error('Erro na autenticação:', err);
      })
      .finally(() => {
        button.innerHTML = originalText;
        button.disabled = false;
      });
    }