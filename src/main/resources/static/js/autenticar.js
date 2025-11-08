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

      // Use caminho relativo para evitar problemas de porta/origem
      fetch("/autenticacao", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        // O backend espera um Usuario com contatoUsuario.email e senha
        body: JSON.stringify({ 
          contatoUsuario: { email: email }, 
          senha: senha 
        })
      })
      .then(res => {
        if (res.ok) {
          erro.innerHTML = '<i class="bi bi-check-circle-fill me-2"></i>Login realizado com sucesso!';
          erro.className = 'text-success small';
          setTimeout(() => {
            window.location.href = '/gestao/dashboard';
          }, 1000);
        } else {
          return res.text().then(texto => {
            erro.innerHTML = '<i class="bi bi-exclamation-triangle-fill me-2"></i>' + texto;
            erro.className = 'text-danger small';
          });
        }
      })
      .catch(err => {
        erro.innerHTML = '<i class="bi bi-wifi-off me-2"></i>Erro de conexão.';
        erro.className = 'text-danger small';
        console.error(err);
      })
      .finally(() => {
        button.innerHTML = originalText;
        button.disabled = false;
      });
    }