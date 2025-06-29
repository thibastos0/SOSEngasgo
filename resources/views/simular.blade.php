<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SOS Engasgo - Simular</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <style>
        
        body {
            font-family: sans-serif;
            background-color: #FBECEC;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .content-box {
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 { color: #333; }
        p { color: #666; }
        a { color: #e53e3e; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .video-link {
            margin-top: 20px;
            display: block; 
            font-size: 1.1em;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="content-box">
        <h1>Página de Simulação</h1>
        <p>Enquanto o socorro não vem.</p>

       
        <a href="https://www.youtube.com/watch?v=GKHM3JW8y8w" target="_blank" class="video-link">
            Assista ao vídeo de ajuda: Como salvar uma pessoa engasgada
        </a>
        <p><a href="/">Voltar para a Home</a></p>
    </div>
</body>
</html>