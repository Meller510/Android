package com.example.m16_architecture.entity

import com.google.gson.annotations.SerializedName

interface PokemonInfo {
    val id: Int
    val name: String
    val sprites: Sprites

    fun sprite() = sprites.other?.officialArtwork?.frontDefault
}

class Sprites {
    @SerializedName("other")
    val other: Other? = null
}

class Other {
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork? = null
}

class OfficialArtwork {
    @SerializedName("front_default")
    val frontDefault: String? = null
}