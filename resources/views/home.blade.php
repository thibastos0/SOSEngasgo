<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SOS Engasgo</title>
    
    @vite(['resources/css/app.css', 'resources/js/app.js'])
</head>
<body>
    <div class="app-container">
        <div class="top-bar">
            <div class="settings-icon">
                <img src="{{ asset('img/chavefenda.png') }}" alt="Configurações">
            </div>
        </div>

        <div class="logo-section">
            <div class="logo-icon">
                <img src="{{ asset('img/cruz.png') }}" alt="SOS Engasgo">
            </div>
            <h1>SOS Engasgo</h1>
        </div>

       <div class="main-button">
    
    <a href="{{ url('/emergencia') }}"><button>Emergência</button></a>
</div>

<div class="bottom-buttons">
   
    <a href="{{ url('/aprender') }}" class="learn-button">
        <img src="{{ asset('img/livro.png') }}" alt="Aprender">
        <span>Aprender</span>
    </a>
    
    <a href="{{ url('/simular') }}" class="simulate-button">
        <img src="{{ asset('img/play.png') }}" alt="Simular">
        <span>Simular</span>
    </a>
</div>
    </div>
</body>
</html>