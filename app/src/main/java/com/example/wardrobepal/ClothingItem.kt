package com.example.wardrobepal

enum class ClothingType {
    SHIRT, PANTS
}

data class ClothingItem(
    val name: String,
    val type: ClothingType,
    val color: String
)
