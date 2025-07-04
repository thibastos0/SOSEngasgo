<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-white-800 leading-tight">
            {{ __('Módulo de Emergência') }}
        </h2>
    </x-slot>
    <div class="py-8 bg-blue-900 flex items-center justify-center mt-6 sm:mt-0">
        <div class="max-w-3xl mx-auto sm:px-6 lg:px-8">
            <div class="bg-blue-500/30 backdrop-blur-md border border-white/20 rounded-lg shadow-lg p-8 text-center">
                <h2>Atendimento confirmado pela equipe!</h2>
                <p>ID do atendimento: {{ $atendimento->id }}</p>
                <p>Status: {{ $atendimento->status }}</p>
                <p>Localização: {{ $atendimento->localizacao }}</p>
                <p>Data de usuario: {{ $atendimento->user }}</p>
            </div>
        </div>
    </div>
</x-app-layout>