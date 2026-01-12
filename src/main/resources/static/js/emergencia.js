// Script de acionamento de emergência
// Responsável por enviar alerta e fornecer feedback visual

(function() {
  const emergencyBtn = document.getElementById('emergencyBtn');
  
  if (!emergencyBtn) return;
  
  emergencyBtn.addEventListener('click', function() {
    // Confirmação antes de acionar
    if (!confirm('Confirma o acionamento de EMERGÊNCIA? Todos os responsáveis serão notificados.')) {
      return;
    }
    
    // Desabilitar botão para evitar múltiplos cliques
    emergencyBtn.disabled = true;
    emergencyBtn.style.opacity = '0.6';
    emergencyBtn.style.cursor = 'not-allowed';
    
    // Redirecionar para a página de atendimento/ação
    window.location.assign('/emergencia/acao');
  });
})();
