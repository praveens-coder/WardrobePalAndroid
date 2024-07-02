package com.example.wardrobepal

data class ClothingItem(
    val name: String,
    val color: String,
    val type: ClothingType
)

enum class ClothingType {
    SHIRT,
    PANTS
}
