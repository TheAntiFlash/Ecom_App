package com.example.ecomapp.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.ecomapp.domain.model.Product
import com.example.ecomapp.domain.model.Response
import com.example.ecomapp.domain.model.Response.Success
import com.example.ecomapp.domain.model.Response.Loading
import com.example.ecomapp.domain.repository.BooleanResponse
import com.example.ecomapp.domain.repository.ProductRepository
import com.example.ecomapp.domain.repository.ProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repo: ProductRepository) : ViewModel() {
    private val _listOfProducts = MutableLiveData<ProductsResponse>(Loading)
    private val _addProductResponse: MutableLiveData<BooleanResponse> = MutableLiveData(Success(false))
    private val _productNameError = MutableLiveData("")
    private val _priceError = MutableLiveData("")
    private val _discountPriceError = MutableLiveData("")
    private val _quantityError = MutableLiveData("")
    val listOfProducts: LiveData<ProductsResponse>
        get() = _listOfProducts
    val addProductResponse: LiveData<BooleanResponse>
        get() = _addProductResponse

     val productNameError: LiveData<String>
         get() = _productNameError
     val priceError: LiveData<String>
         get() = _priceError
     val discountPriceError: LiveData<String>
         get() = _discountPriceError
     val quantityError: LiveData<String>
         get() = _quantityError




    fun addProduct(
        productName: String,
        price: String,
        discountPrice: String? = null,
        quantity: String,
        imgRes: Int,
    ) = viewModelScope.launch {
        _addProductResponse.value = Loading
        if ( validateFields(productName, price, discountPrice, quantity) )
        {
            try {
                repo.addProduct(
                    Product(
                        name = productName,
                        price = price.toInt(),
                        discountPrice = discountPrice?.toInt(),
                        quantity = quantity.toInt(),
                        imageUrl = "",
                    )
                ).asFlow().collect {
                    _addProductResponse.value = it
                    Log.i("Product", "Add product: $it")

                }
            } catch (e: Exception) {
                Log.w("Adding product" , "failed", e)
            }
        }
    }
    fun resetAddProductResponse() { _addProductResponse.value = Success(false) }
    private fun validateFields(productName: String, price: String, discountPrice: String?, quantity: String): Boolean {

        var fieldsAreValid = true
        val condition = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE)

        val p = Pattern.compile(
            "[^a-z0-9 ]", Pattern.CASE_INSENSITIVE
        )
        val m =  p.matcher(productName)

        if (productName.isEmpty()) {
            fieldsAreValid = false
            _productNameError.value = "product name cannot be empty."
        }
        else if (condition.matcher(productName).find()) {
            fieldsAreValid = false
            _productNameError.value = "product name cannot contain special characters or spaces."
        } else if (productName.length < 2) {
            fieldsAreValid = false
            _productNameError.value = "product name must be at least 2 characters."
        }
        else {
            _productNameError.value = ""
        }

        if (price.isEmpty()) {
            fieldsAreValid = false
            _priceError.value = "price cannot be empty."
        }
        else {
            _priceError.value = ""
        }

        try {
            var checkingPrice = price.toInt()
        } catch (e : NumberFormatException) {
            _priceError.value = "enter a valid integer"
        }

        if ( discountPrice != null ) {

            if (discountPrice.isEmpty()) {
                fieldsAreValid = false
                _discountPriceError.value = "discount cannot be empty."
            } else {
                _discountPriceError.value = ""
            }

            try {
                var checkingPrice = discountPrice.toInt()
            } catch (e: NumberFormatException) {
                _discountPriceError.value = "enter a valid integer"
            }
        }

        if (quantity.isEmpty()) {
            fieldsAreValid = false
            _quantityError.value = "quantity cannot be empty."
        } else {
            _quantityError.value = ""
        }

        try {
            var checkingPrice = quantity.toInt()
        } catch (e: NumberFormatException) {
            _quantityError.value = "enter a valid integer"
        }
        return fieldsAreValid
    }


    fun loadProducts() = viewModelScope.launch{
        _listOfProducts.value = Loading
        repo.getAllProducts().asFlow().collect {
            _listOfProducts.value = it
        }
    }


}