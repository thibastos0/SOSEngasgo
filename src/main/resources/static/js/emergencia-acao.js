(function() {
  // Estado
  let mediaUrls = []; // preenchido via API
  let currentIndex = 0;
  let isVideo = false;
  let isPlaying = false;

  const mediaContainer = document.getElementById('mediaContainer');
  const messagesList = document.getElementById('messagesList');
  const prevBtn = document.getElementById('prevBtn');
  const nextBtn = document.getElementById('nextBtn');
  const playPauseBtn = document.getElementById('playPauseBtn');
  const encerrarBtn = document.getElementById('encerrarBtn');

  // Util
  function isVideoUrl(url) {
    return /\.(mp4|webm)(\?.*)?$/i.test(url);
  }

  function clearMedia() {
    while (mediaContainer.firstChild) mediaContainer.removeChild(mediaContainer.firstChild);
  }

  function renderMedia(url) {
    clearMedia();
    isVideo = isVideoUrl(url);
    isPlaying = false;

    if (isVideo) {
      const video = document.createElement('video');
      video.src = url;
      video.playsInline = true;
      video.loop = true;
      video.muted = true; // garantir reprodução automática sem interação
      video.controls = false;
      video.autoplay = true;
      video.addEventListener('play', () => { isPlaying = true; updatePlayPauseIcon(); });
      video.addEventListener('pause', () => { isPlaying = false; updatePlayPauseIcon(); });
      // id=mediaContainer
      mediaContainer.appendChild(video);
      // tenta iniciar
      video.play().catch(() => { /* pode falhar em alguns browsers sem interação */ });
      isPlaying = true;
    } else {
      const img = document.createElement('img');
      img.alt = 'Manobra de desengasgo';
      img.src = url;
      // id=mediaContainer
      mediaContainer.appendChild(img);
      isPlaying = false; // GIF não possui play/pause confiável
    }
    updatePlayPauseIcon();
    updateControlsState();
  }

  function updatePlayPauseIcon() {
    const icon = playPauseBtn.querySelector('i');
    icon.className = isPlaying ? 'fa-solid fa-pause' : 'fa-solid fa-play';
    playPauseBtn.disabled = !isVideo; // só habilita se for vídeo
    playPauseBtn.title = isVideo ? (isPlaying ? 'Pausar' : 'Reproduzir') : 'Controle indisponível para GIF';
  }

  function updateControlsState() {
    prevBtn.disabled = mediaUrls.length <= 1;
    nextBtn.disabled = mediaUrls.length <= 1;
  }

  function showIndex(idx) {
    if (!mediaUrls.length) return;
    currentIndex = (idx + mediaUrls.length) % mediaUrls.length;
    renderMedia(mediaUrls[currentIndex]);
  }

  // Eventos
  prevBtn.addEventListener('click', () => showIndex(currentIndex - 1));
  nextBtn.addEventListener('click', () => showIndex(currentIndex + 1));
  playPauseBtn.addEventListener('click', () => {
    if (!isVideo) return;
    const video = mediaContainer.querySelector('video');
    if (!video) return;
    if (video.paused) {
      video.play();
      isPlaying = true;
    } else {
      video.pause();
      isPlaying = false;
    }
    updatePlayPauseIcon();
  });

  encerrarBtn.addEventListener('click', () => {
    const confirmar = confirm('Deseja encerrar e voltar à página anterior?');
    if (confirmar) {
      // Poderia navegar para '/emergencia' diretamente; usar histórico melhora UX
      window.history.length > 1 ? window.history.back() : window.location.assign('/emergencia');
    }
  });

  // Mensagens (placeholder). Futuro: SSE/WebSocket/long-poll de webhook
  function appendMessage(text) {
    const li = document.createElement('li');
    li.className = 'list-group-item';
    li.textContent = text;
    // id=messagesList
    messagesList.appendChild(li);
    // scroll para última
    const scroll = document.getElementById('messagesScroll');
    scroll.scrollTop = scroll.scrollHeight;
  }

  // Carregar mídias do backend
  function loadMedia() {
    fetch('/api/emergencia/gifs', { credentials: 'same-origin' })
      .then(r => r.ok ? r.json() : Promise.reject(new Error('Falha ao obter GIFs')))
      .then(urls => {
        if (!Array.isArray(urls) || urls.length === 0) throw new Error('Lista vazia');
        mediaUrls = urls;
        showIndex(0);
      })
      .catch(() => {
        // Fallback: exemplo com links públicos (substitua por conteúdo real)
        mediaUrls = [
          'https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExY2FvMXRndHJjcTltM2EyajVobHNlN2xrazdqdzloaWRzODYzeXAzYSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/xT5LMSXkjlIArjlB9m/giphy.gif',
          'https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExcWJxd21naW1leXI4Y3Q3MTh4N2l0ZGpxbGh3NzdiNTRibmwwYjRhYyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/l2Je23BqLOdtdnrws/giphy.gif'
        ];
        showIndex(0);
        appendMessage('Nenhuma lista encontrada; exibindo conteúdo de exemplo.');
      });
  }

  function initDemoMessages() {
    // Exemplo de mensagens chegando a cada 4s
    const msgs = [
      'Atendente João: A caminho da sala 2.',
      'Orientação: Posicionar a criança com leve inclinação.',
      'Orientação: Aplicar 5 tapas interescapulares, avaliar respiração.',
      'Atendente Maria: Reforçando equipe no local.'
    ];
    let i = 0;
    setInterval(() => {
      appendMessage(msgs[i % msgs.length]);
      i++;
    }, 4000);
  }

  // Boot
  loadMedia();
  initDemoMessages();
})();
