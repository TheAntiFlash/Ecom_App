package com.example.ecomapp.vm

import androidx.lifecycle.ViewModel
import com.example.ecomapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(repo : ProductRepository): ViewModel() {

}