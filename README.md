<p align="center"><a href="https://laravel.com" target="_blank"><img src="https://raw.githubusercontent.com/laravel/art/master/logo-lockup/5%20SVG/2%20CMYK/1%20Full%20Color/laravel-logolockup-cmyk-red.svg" width="400" alt="Laravel Logo"></a></p>

<p align="center">
<a href="https://github.com/laravel/framework/actions"><img src="https://github.com/laravel/framework/workflows/tests/badge.svg" alt="Build Status"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/dt/laravel/framework" alt="Total Downloads"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/v/laravel/framework" alt="Latest Stable Version"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/l/laravel/framework" alt="License"></a>
</p>

## About Laravel

Laravel is a web application framework with expressive, elegant syntax. We believe development must be an enjoyable and creative experience to be truly fulfilling. Laravel takes the pain out of development by easing common tasks used in many web projects, such as:

- [Simple, fast routing engine](https://laravel.com/docs/routing).
- [Powerful dependency injection container](https://laravel.com/docs/container).
- Multiple back-ends for [session](https://laravel.com/docs/session) and [cache](https://laravel.com/docs/cache) storage.
- Expressive, intuitive [database ORM](https://laravel.com/docs/eloquent).
- Database agnostic [schema migrations](https://laravel.com/docs/migrations).
- [Robust background job processing](https://laravel.com/docs/queues).
- [Real-time event broadcasting](https://laravel.com/docs/broadcasting).

Laravel is accessible, powerful, and provides tools required for large, robust applications.

## Learning Laravel

Laravel has the most extensive and thorough [documentation](https://laravel.com/docs) and video tutorial library of all modern web application frameworks, making it a breeze to get started with the framework.

You may also try the [Laravel Bootcamp](https://bootcamp.laravel.com), where you will be guided through building a modern Laravel application from scratch.

If you don't feel like reading, [Laracasts](https://laracasts.com) can help. Laracasts contains thousands of video tutorials on a range of topics including Laravel, modern PHP, unit testing, and JavaScript. Boost your skills by digging into our comprehensive video library.

## Laravel Sponsors

We would like to extend our thanks to the following sponsors for funding Laravel development. If you are interested in becoming a sponsor, please visit the [Laravel Partners program](https://partners.laravel.com).

### Premium Partners

- **[Vehikl](https://vehikl.com/)**
- **[Tighten Co.](https://tighten.co)**
- **[WebReinvent](https://webreinvent.com/)**
- **[Kirschbaum Development Group](https://kirschbaumdevelopment.com)**
- **[64 Robots](https://64robots.com)**
- **[Curotec](https://www.curotec.com/services/technologies/laravel/)**
- **[Cyber-Duck](https://cyber-duck.co.uk)**
- **[DevSquad](https://devsquad.com/hire-laravel-developers)**
- **[Jump24](https://jump24.co.uk)**
- **[Redberry](https://redberry.international/laravel/)**
- **[Active Logic](https://activelogic.com)**
- **[byte5](https://byte5.de)**
- **[OP.GG](https://op.gg)**

## Contributing

Thank you for considering contributing to the Laravel framework! The contribution guide can be found in the [Laravel documentation](https://laravel.com/docs/contributions).

## Code of Conduct

In order to ensure that the Laravel community is welcoming to all, please review and abide by the [Code of Conduct](https://laravel.com/docs/contributions#code-of-conduct).

## Security Vulnerabilities

If you discover a security vulnerability within Laravel, please send an e-mail to Taylor Otwell via [taylor@laravel.com](mailto:taylor@laravel.com). All security vulnerabilities will be promptly addressed.

## License

The Laravel framework is open-sourced software licensed under the [MIT license](https://opensource.org/licenses/MIT).

## Etapas propostas

- Resumo do que você precisa configurar
	•	Criar um bot no BotFather.
	•	Configurar o webhook para receber eventos do bot.
	•	Colocar seu PHP em um servidor com HTTPS (requisito do Telegram).
	•	Obter o chat_id do terceiro (pode ser grupo, canal, etc.).
	•	Lidar com os fluxos de segurança (consentimento de localização, confirmação, etc.).

## Passos para criar o bot

	1.	No Telegram, busque por: @BotFather
	2.	Envie: /start
	3.	Depois: /newbot
	4.	Dê um nome e um @username único para o bot.
	5.	O BotFather vai te dar um token, algo como: 123456789:ABCDefghIJKlmnoPQrstuvXYZ1234567890

## Instalar a biblioteca Telegram no Laravel

- No terminal do seu projeto Laravel:
composer require irazasyed/telegram-bot-sdk

## Webhook

- php artisan make:command SetTelegramWebhook
- .env: APP_URL deve conter a URL atualizada
- php artisan telegram:webhook
- editado csrf com execção para webhook: bootstrap\cache\app.php
- Obs.: Webhook funcionou com rota fora da middleware(auth) somente

## Instalação
`bash`
- git clone [SOSEngasgo](https://github.com/seu-usuario/SOSEngasgo.git).
- cd SOSEngasgo
- composer install
- npm install
- copiar .env (compartilhado no grupo da equipe) para a pasta do projeto
- npm run dev `ou npm run dev build (desenvolvimento x deploy)`
- php artisan serve
- Obs.: necessário criar o database (database.sqlite) ou copiar o enviado no grupo da equipe para ./database/.
- php artisan migrate (para estrutura do BD)

## Criar branch de trabalho
- git checkout -b feature/nome-da-funcionalidade

## Fazer modificação e commit
- git add .
- git commit -m "Explicar funcionalidade programada"

## Enviar branch para o repositório remoto
- git push -u origin nome-da-branch

## Sincronizar repositório
- git pull origin develop `ou main`

## Testes no Render
- [sos-engasgo](https://sos-engasgo.onrender.com/).

## Trello
- [Trello](https://trello.com/b/Xiy0EM2K).

## Diversos
- App\Providers\AppServiceProvider.php: force HTTPS
- helpcommand \help já vem com API;
