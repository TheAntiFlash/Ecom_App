package com.example.ecomapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivityAddItemBinding
import com.example.ecomapp.domain.model.Response.Loading
import com.example.ecomapp.domain.model.Response.Success
import com.example.ecomapp.domain.model.Response.Failure
import com.example.ecomapp.vm.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import org.checkerframework.common.subtyping.qual.Bottom

@AndroidEntryPoint
class AddItemActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddItemBinding
    private val viewModel by viewModels<ProductViewModel>()
    private lateinit var bottomSheetDialog : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initViewModelLiveData()
        initBottomSheet()
        initDropDownMenu()
        initListeners()
    }

    private fun initBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_gallery_camera_layoyt)

    }

    private fun initViewModelLiveData() {
        viewModel.productNameError.observe(this) {
            binding.etProductNameContainer.error = SpannableStringBuilder(it ?: "")
        }
        viewModel.priceError.observe(this) {
            binding.etPriceContainer.error = SpannableStringBuilder(it ?: "")
        }
        viewModel.quantityError.observe(this) {
            binding.spinnerItemQuantity.error = SpannableStringBuilder(it ?: "")
        }
        viewModel.discountPriceError.observe(this) {
            binding.autoCompleteTextViewItemQuantity.error = SpannableStringBuilder(it ?: "")
        }

    }


    private fun initDropDownMenu() {
        val itemQuantity =  resources.getStringArray(R.array.itemQuantity)
        val arrayAdapter = ArrayAdapter(this, R.layout.quantity_drop_down_menu_item, itemQuantity)
        binding.autoCompleteTextViewItemQuantity.setAdapter(arrayAdapter)
    }

    private fun initListeners() {
        binding.apply {

            switchDiscount.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) {
                    binding.etDiscountPriceContainer.visibility = View.VISIBLE
                }
                else {
                    binding.etDiscountPriceContainer.visibility = View.INVISIBLE
                }
            }

            btAddItem.setOnClickListener {
                val discountPrice = if(etDiscountPrice.text.toString() == "") null else etDiscountPrice.text.toString()

                viewModel.addProduct(
                    etProductName.text.toString(),
                    etPrice.text.toString(),
                    discountPrice,
                    etPrice.text.toString(),
                    0,
                )
                viewModel.addProductResponse.observe(this@AddItemActivity) {
                    if (it is Loading) {
                        binding.btAddItem.text = ""
                        binding.progressIndicatorAddItem.visibility = View.VISIBLE
                        binding.btAddItem.isClickable = false

                    }
                    else {
                        binding.btAddItem.text = "Add Item"
                        binding.progressIndicatorAddItem.visibility = View.GONE
                        binding.btAddItem.isClickable = true

                    }
                    when (it) {
                        Success(true) -> {
                            Toast.makeText(this@AddItemActivity, "Product Successfully Added", Toast.LENGTH_SHORT).show()
                            viewModel.resetAddProductResponse()
                        }
                        is Failure -> {
                            Toast.makeText(this@AddItemActivity, "Error: Unable to Add Product", Toast.LENGTH_SHORT).show()
                        }
                        else -> Log.i("Product", "Sending Add Item Req")
                    }
                }

            }

            binding.flProductImageFrame.setOnClickListener {
                bottomSheetDialog.show()
            }

            bottomSheetDialog.findViewById<LinearLayout>(R.id.btCancel)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
        }

    }
}