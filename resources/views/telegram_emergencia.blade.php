    <!DOCTYPE html>
    <html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>SOS Engasgo - Emergência Telegram</title>
        
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
            .telegram-link {
                margin-top: 20px;
                display: inline-block; 
                font-size: 1.2em;
                font-weight: bold;
                background-color: #0088CC; 
                color: white;
                padding: 15px 30px;
                border-radius: 5px;
                text-transform: uppercase;
                text-decoration: none; 
                transition: background-color 0.3s ease;
            }
            .telegram-link:hover {
                background-color: #007bb6;
            }
        </style>
    </head>
    <body>
        <div class="content-box">
            <h1>Emergência SOS Engasgo</h1>
            <p>Em caso de emergência, entre em contato com nosso bot no Telegram para assistência imediata.</p>
    
            <a href="https://t.me/Engasgobot" target="_blank" class="telegram-link">
                Iniciar Atendimento no Telegram
            </a>
            <p style="margin-top: 20px;"><a href="/">Voltar para a Home</a></p>
        </div>
    </body>
    </html>
    