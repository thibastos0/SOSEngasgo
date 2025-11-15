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
    
    // TODO: Implementar chamada à API de acionamento
    // fetch('/api/emergencia/acionar', { 
    //   method: 'POST',
    //   headers: { 'Content-Type': 'application/json' },
    //   credentials: 'same-origin',
    //   body: JSON.stringify({ timestamp: new Date().toISOString() })
    // })
    // .then(response => response.json())
    // .then(data => {
    //   alert('✓ Emergência acionada com sucesso! Ajuda está a caminho.');
    // })
    // .catch(err => {
    //   console.error('Erro ao acionar emergência:', err);
    //   alert('✗ Erro ao acionar emergência. Tente novamente.');
    //   emergencyBtn.disabled = false;
    //   emergencyBtn.style.opacity = '1';
    //   emergencyBtn.style.cursor = 'pointer';
    // });
    
    // Placeholder: simular sucesso imediato
    alert('✓ Emergência acionada com sucesso!\n\nAjuda está a caminho.');
    emergencyBtn.disabled = false;
    emergencyBtn.style.opacity = '1';
    emergencyBtn.style.cursor = 'pointer';
  });
})();
