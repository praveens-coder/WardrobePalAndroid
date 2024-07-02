package com.example.wardrobepal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextClothingName: EditText
    private lateinit var editTextClothingType: EditText
    private lateinit var editTextClothingColor: EditText
    private lateinit var buttonAddClothing: Button
    private lateinit var buttonSuggestOutfit: Button
    private lateinit var textViewSuggestedOutfit: TextView

    private val wardrobe: MutableList<ClothingItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        editTextClothingName = findViewById(R.id.editTextClothingName)
        editTextClothingType = findViewById(R.id.editTextClothingType)
        editTextClothingColor = findViewById(R.id.editTextClothingColor)
        buttonAddClothing = findViewById(R.id.buttonAddClothing)
        buttonSuggestOutfit = findViewById(R.id.buttonSuggestOutfit)
        textViewSuggestedOutfit = findViewById(R.id.textViewSuggestedOutfit)

        // Set click listeners
        buttonAddClothing.setOnClickListener { addClothing() }
        buttonSuggestOutfit.setOnClickListener { suggestOutfit() }
    }

    private fun addClothing() {
        val name = editTextClothingName.text.toString().trim()
        val typeString = editTextClothingType.text.toString().trim()
        val color = editTextClothingColor.text.toString().trim()

        // Convert typeString to ClothingType enum
        val type = when (typeString.lowercase()) {
            "shirt" -> ClothingType.SHIRT
            "pants" -> ClothingType.PANTS
            else -> throw IllegalArgumentException("Unknown clothing type: $typeString")
        }

        if (name.isNotEmpty() && color.isNotEmpty()) {
            val clothingItem = ClothingItem(name, color, type)
            wardrobe.add(clothingItem)
            clearFields()
            // Optionally, you can display a message or update UI after adding
        } else {
            // Handle case where fields are empty
            // Optionally, display a toast or message to the user
        }
    }


    private fun suggestOutfit() {
        if (wardrobe.isEmpty()) {
            textViewSuggestedOutfit.text = "Add items to your wardrobe first."
        } else {
            val randomIndex = (0 until wardrobe.size).random()
            val suggestedItem = wardrobe[randomIndex]
            textViewSuggestedOutfit.text = "Suggested outfit: ${suggestedItem.name}, ${suggestedItem.color}, ${suggestedItem.type}"
        }
    }

    private fun clearFields() {
        editTextClothingName.text.clear()
        editTextClothingType.text.clear()
        editTextClothingColor.text.clear()
    }
}
