<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SOS Engasgo - Emergência</title>
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
        h1 { color: #e53e3e; } 
        p { color: #666; }
        a { color: #e53e3e; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="content-box">
        <h1>MODO DE EMERGÊNCIA ATIVADO!</h1>
        <p>canais de atendimento</p>
        <ul>
            <li>Ligar para o número de emergência (Bombeiro 193 /SAMU, 192)</li>
            <li>Ver instruções rápidas de primeiros socorros</li>
            <li>CADASTRO</li>
        </ul>
        <p><a href="/">Voltar para a Home</a></p>
    </div>
</body>
</html>