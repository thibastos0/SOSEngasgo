<?php

use App\Http\Controllers\ProfileController;
use App\Http\Controllers\TelegramBotController;
use Illuminate\Support\Facades\Route;
use Telegram\Bot\Laravel\Facades\Telegram;


Route::get('/', function () {
    return view('welcome');
});

Route::get('/dashboard', function () {
    return view('dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');

    // Telegram Bot Route
    Route::get('/send-message_old', function () {
        $chatId = env('CHAT_ID_TELEGRAM', 'erro'); // Get chat ID from .env file
        $message = "Olá mundo! Mensagem via Laravel com id $chatId!"; 
        // ID Bot = 7807867662
    
        Telegram::sendMessage([
            'chat_id' => $chatId,
            'text' => $message,
        ]);
    
        return 'Message sent to Telegram!';
    });

    Route::get('/send-message', [TelegramBotController::class, 'sendMessage'])->name('send-message');

    Route::get('/get-updates', [TelegramBotController::class, 'getUpdates'])->name('get-updates');

    Route::get('/get-me', [TelegramBotController::class, 'getMe'])->name('get-me');

/*
    Route::get('/set-webhook', function () {
        $url = env('TELEGRAM_WEBHOOK_URL');
        $response = Telegram::setWebhook(['url' => $url]);
        return $response;
    });
    Route::get('/delete-webhook', function () {
        $response = Telegram::deleteWebhook();
        return $response;
    });*/
});

require __DIR__.'/auth.php';
