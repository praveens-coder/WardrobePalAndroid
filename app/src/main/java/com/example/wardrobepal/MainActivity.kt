package com.example.wardrobepal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextClothingName: EditText
    private lateinit var spinnerClothingType: Spinner
    private lateinit var editTextClothingColor: EditText
    private lateinit var buttonAddClothing: Button
    private lateinit var buttonDisplayWardrobe: Button
    private lateinit var buttonSuggestOutfit: Button
    private lateinit var textViewSuggestedOutfit: TextView

    private val wardrobe: MutableList<ClothingItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        editTextClothingName = findViewById(R.id.editTextClothingName)
        spinnerClothingType = findViewById(R.id.spinnerClothingType)
        editTextClothingColor = findViewById(R.id.editTextClothingColor)
        buttonAddClothing = findViewById(R.id.buttonAddClothing)
        buttonDisplayWardrobe = findViewById(R.id.buttonDisplayWardrobe)
        buttonSuggestOutfit = findViewById(R.id.buttonSuggestOutfit)
        textViewSuggestedOutfit = findViewById(R.id.textViewSuggestedOutfit)

        // Set click listeners
        buttonAddClothing.setOnClickListener { addClothing() }
        buttonDisplayWardrobe.setOnClickListener { displayWardrobe() }
        buttonSuggestOutfit.setOnClickListener { suggestOutfit() }
    }

    private fun addClothing() {
        val name = editTextClothingName.text.toString().trim()
        val color = editTextClothingColor.text.toString().trim()
        val type = when (spinnerClothingType.selectedItem.toString()) {
            getString(R.string.clothing_type_shirt) -> ClothingType.SHIRT
            getString(R.string.clothing_type_pants) -> ClothingType.PANTS
            getString(R.string.clothing_type_jacket) -> ClothingType.JACKET
            getString(R.string.clothing_type_accessory) -> ClothingType.ACCESSORY
            else -> throw IllegalArgumentException(getString(R.string.error_invalid_type))
        }

        if (name.isNotEmpty() && color.isNotEmpty()) {
            val clothingItem = ClothingItem(name, color, type)
            wardrobe.add(clothingItem)
            clearFields()
            // Optionally, display a message or update UI after adding
        } else {
            // Handle case where fields are empty
            // Optionally, display a toast or message to the user
        }
    }

    private fun displayWardrobe() {
        if (wardrobe.isEmpty()) {
            textViewSuggestedOutfit.text = getString(R.string.empty_wardrobe_message)
        } else {
            val wardrobeItems = wardrobe.joinToString("\n") { "${it.name} (${it.color}, ${it.type})" }
            textViewSuggestedOutfit.text = wardrobeItems
        }
    }

    private fun suggestOutfit() {
        // Group items by their type
        val groupedItems = wardrobe.groupBy { it.type }

        // Collect one item of each type if available
        val suggestedItems = mutableListOf<ClothingItem>()
        ClothingType.values().forEach { type ->
            groupedItems[type]?.let { items ->
                if (items.isNotEmpty()) {
                    suggestedItems.add(items.random())
                }
            }
        }

        // Handle case where no items are in the wardrobe
        if (suggestedItems.isEmpty()) {
            textViewSuggestedOutfit.text = getString(R.string.empty_wardrobe_message)
        } else {
            // Create a display string for the selected items
            val outfitDescription = suggestedItems.joinToString(separator = ", ") {
                "${it.name} (${it.color}, ${it.type.name.lowercase().replaceFirstChar { it.uppercase() }})"
            }
            textViewSuggestedOutfit.text = getString(R.string.suggest_outfit_message, outfitDescription)
        }
    }

    private fun clearFields() {
        editTextClothingName.text.clear()
        spinnerClothingType.setSelection(0)  // Reset spinner to the first item
        editTextClothingColor.text.clear()
    }
}
