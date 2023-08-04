package com.example.ecomapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivityAddItemBinding
import com.example.ecomapp.databinding.ActivityMainBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initListeners()
        initDropDownMenu()
    }

    private fun initDropDownMenu() {
        val itemQuantity =  resources.getStringArray(R.array.itemQuantity)
        val arrayAdapter = ArrayAdapter(this, R.layout.quantity_drop_down_menu_item, itemQuantity)
        binding.autoCompleteTextViewItemQuantity.setAdapter(arrayAdapter)
    }

    private fun initListeners() {
        binding.switchDiscount.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                binding.etDiscountPriceContainer.visibility = View.VISIBLE
            }
            else {
                binding.etDiscountPriceContainer.visibility = View.INVISIBLE
            }
        }
    }
}