<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Telegram\Bot\Laravel\Facades\Telegram;
use Illuminate\Support\Facades\Auth;
use App\Models\Atendimento;

class TelegramBotController extends Controller

{
    public function dashboard()
    {
        $user = Auth::user();
        //é possível fazer um if e verificar se o usuário é Administrador e pode ver todas os atendimentos
        $atendimentos = $user->atendimentos()->orderBy('created_at', 'desc')->get(); //lista de atendientos do usuário logado
        return view('dashboard', compact('atendimentos')); //compact é um helper do PHP que transforma variáveis em array associativo
    }

    public function index()
    {
        return view('telegram.atendimento');
    }

    public function iniciarAtendimento()
    {   
        $user = Auth::user();
        if (!$user) {
            return redirect()->route('telegram.atendimento')->with('error', 'Usuário não autenticado.');
        }
        $atendimento = Atendimento::create([
            'status' => 'aguardando',
            'user_id' => $user->id, // ID do usuário autenticado
        ]);
        
        // Enviar mensagem para o grupo do Telegram
        $response = Telegram::sendMessage([
            'chat_id' => env('CHAT_ID_TELEGRAM_GROUP'), // ID do chat,
            'text' => 'Olá Equipe! Atendimento solicitado pelo ' .$atendimento->user_id .' - ' .$user->name .' com ID: ' . $atendimento->id . ' - responder ao bot com "ok" ou "recebido" para confirmar. Ass.: Laravel.',
        ]);

        //message_id do atendimento para que o "ok" possa ser direcionado ao chamado de emergência corretamente
        $atendimento->telegram_message_id = $response->getMessageId(); 
        $atendimento->save();

        return redirect()->route('telegram.aguardando', ['id' => $atendimento->id]);
    }

    public function aguardandoAtendimento($id)
    {
        //dd($id);
        $atendimento = Atendimento::findOrFail($id);
        if (!$atendimento->id){
            return redirect()->route('telegram.atendimento');
        }
        
        if(!$this->validarAcesso($atendimento)){
            return redirect()->route('telegram.atendimento')->with('error', 'Você não tem permissão para acessar esta id!');
        }

        // Verifica se o atendimento foi confirmado
        if ($atendimento->status === 'confirmado') {
            return redirect()->route('telegram.confirmado', ['id' => $atendimento->id]);
        }

        // compact envia a variável $atendimento para a view aguardando.blade.php
        return view('telegram.aguardando', compact('atendimento'));
    }

    public function verificarConfirmacao($id)
    {
        //dd($id);
        $atendimento = Atendimento::findOrFail($id);
        if (!$atendimento->id){            
            return response()->json(['confirmado' => false]);
        }

        if(!$this->validarAcesso($atendimento)){
            return response()->json(['confirmado' => false, 'error' => 'Você não tem permissão para acessar esta id!'], 403);
        }

        return response()->json([
            'confirmado' => $atendimento && $atendimento->status === 'confirmado',
            'message_id' => $atendimento->telegram_message_id,
        ]);
    }

    public function confirmarAtendimento($id)
    {
        //dd($id);
        $atendimento = Atendimento::findOrFail($id);

        if (!$atendimento->id){
            return redirect()->route('telegram.atendimento');
        }

        if(!$this->validarAcesso($atendimento)){
            return redirect()->route('telegram.atendimento')->with('error', 'Você não tem permissão para acessar esta id!');
        }
        $atendimento->dataConfirmado = now();
        $atendimento->status = 'confirmado';
        $atendimento->save();

        return view('telegram.confirmado', compact('atendimento'));
    }

        private function validarAcesso(Atendimento $atendimento):bool
    {
        $user = Auth::user();
        if($atendimento->user_id != $user->id){
            return false;
        }

        return true;

    }

}