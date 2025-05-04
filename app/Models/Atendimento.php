<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Atendimento extends Model
{
    protected $fillable = [
        'user_id',
        'status',
        'dataConfirmado',
        'localizacao',
        'telegram_message_id',
    ];

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

}
