// Script de apoio ao formulário de cadastro/edição de usuário
// Responsabilidade: UI helpers específicos do formulário (ex: mostrar/ocultar senha)
// Se crescer, considerar modularizar ou migrar lógica de fetch/validação para usuario.js

(function() {
  function togglePassword() {
    const senhaInput = document.getElementById('senha');
    if (!senhaInput) return;
    const toggleIcon = document.getElementById('toggleIcon');
    if (senhaInput.type === 'password') {
      senhaInput.type = 'text';
      if (toggleIcon) {
        toggleIcon.classList.remove('fa-eye');
        toggleIcon.classList.add('fa-eye-slash');
      }
    } else {
      senhaInput.type = 'password';
      if (toggleIcon) {
        toggleIcon.classList.remove('fa-eye-slash');
        toggleIcon.classList.add('fa-eye');
      }
    }
  }

  // Expor função global mínima usada pelo onclick inline, mantendo escopo isolado.
  window.togglePassword = togglePassword;
})();
