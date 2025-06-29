<?php

use App\Http\Controllers\AtendimentoController;
use App\Http\Controllers\ProfileController;
use App\Http\Controllers\TelegramBotController;
use App\Http\Controllers\TelegramController;
use Illuminate\Support\Facades\Route;
use Telegram\Bot\Laravel\Facades\Telegram;

// Apagar depois
use Illuminate\Support\Facades\Artisan;


Route::get('/', function () {
    return view('welcome');
});

//Route::get('/dashboard', function () {
//    return view('dashboard');
//})->middleware(['auth', 'verified'])->name('dashboard');
// Lista Atendimentos
Route::get('/dashboard', [TelegramBotController::class, 'dashboard'])->middleware(['auth', 'verified'])->name('dashboard');
Route::delete('/dashboard/{atendimento}', [AtendimentoController::class, 'destroy'])->middleware(['auth', 'verified'])->name('destroy');

// Webhook (Telegram chama automaticamente)    
Route::post('telegram/webhook', [TelegramController::class, 'webhook']);

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');

    //Telegram Bot
    Route::get('/get-updates', function () {
        $updates = Telegram::getUpdates();
        return response()->json($updates);
    })->name('telegram.get.updates');

    Route::get('/send-message', function () {
        $response = Telegram::sendMessage([
            'chat_id' => env('CHAT_ID_TELEGRAM_GROUP'), // ID do chat
            'text' => 'Olá do Laravel!'
        ]);
        return response()->json($response);
    })->name('telegram.send.message');

    Route::get('get-me', function () {
        $me = Telegram::getMe();
        return response()->json($me);
    })->name('telegram.get.me');

    // Atendimento
    Route::get('/atendimento', [TelegramBotController::class, 'index'])->name('telegram.atendimento');
    Route::post('/atendimento', [TelegramBotController::class, 'iniciarAtendimento'])->name('telegram.atendimento.iniciar'); // clique no botão "iniciar atendimento"

    // Tela aguardando
    Route::get('/aguardando/{id}', [TelegramBotController::class, 'aguardandoAtendimento'])->name('telegram.aguardando');
    //Route::get('/aguardando', [TelegramBotController::class, 'aguardandoAtendimento'])->name('telegram.aguardando');
    // Verifica se o atendimento foi confirmado pelo webhook e atualiza o status
    Route::get('/verificar/{id}', [TelegramBotController::class, 'verificarConfirmacao'])->name('telegram.verificar');
    
    // Confirmar atendimento
    Route::get('/confirmacao/{id}', [TelegramBotController::class, 'confirmarAtendimento'])->name('telegram.confirmado');

    // Temporário
    Route::get('/limpar-cache', function () {
        Artisan::call('optimize:clear');
        return 'Cache limpo!';
    });


});

require __DIR__.'/auth.php';