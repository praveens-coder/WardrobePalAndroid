package com.example.wardrobepal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

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
        if (wardrobe.isEmpty()) {
            textViewSuggestedOutfit.text = getString(R.string.suggest_outfit_empty)
        } else {
            val suggestedItems = mutableListOf<String>()

            // Collect items by type
            val shirts = wardrobe.filter { it.type == ClothingType.SHIRT }.map { it.name }
            val pants = wardrobe.filter { it.type == ClothingType.PANTS }.map { it.name }
            val jackets = wardrobe.filter { it.type == ClothingType.JACKET }.map { it.name }
            val accessories = wardrobe.filter { it.type == ClothingType.ACCESSORY }.map { it.name }

            if (shirts.isNotEmpty()) suggestedItems.add(getString(R.string.suggested_shirt) + ": " + shirts.joinToString(", "))
            if (pants.isNotEmpty()) suggestedItems.add(getString(R.string.suggested_pants) + ": " + pants.joinToString(", "))
            if (jackets.isNotEmpty()) suggestedItems.add(getString(R.string.suggested_jacket) + ": " + jackets.joinToString(", "))
            if (accessories.isNotEmpty()) suggestedItems.add(getString(R.string.suggested_accessory) + ": " + accessories.joinToString(", "))

            if (suggestedItems.isEmpty()) {
                textViewSuggestedOutfit.text = getString(R.string.empty_wardrobe_message)
            } else {
                textViewSuggestedOutfit.text = getString(R.string.suggest_outfit_message, suggestedItems.joinToString(", "))
            }
        }
    }

    private fun clearFields() {
        editTextClothingName.text.clear()
        spinnerClothingType.setSelection(0)  // Reset spinner to the first item
        editTextClothingColor.text.clear()
    }
}
