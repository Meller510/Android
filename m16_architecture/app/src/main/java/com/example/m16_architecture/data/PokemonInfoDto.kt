package com.example.m16_architecture.data

import com.example.m16_architecture.entity.PokemonInfo
import com.example.m16_architecture.entity.Sprites

class PokemonInfoDto(
    override val id: Int,
    override val name: String,
    override val sprites: Sprites
) : PokemonInfo