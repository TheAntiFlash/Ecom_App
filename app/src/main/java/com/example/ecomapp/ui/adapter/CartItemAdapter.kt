package com.example.ecomapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomapp.R
import com.example.ecomapp.databinding.RecyclerCardViewDesignBinding
import com.example.ecomapp.databinding.RecyclerCartItemCardViewDesignBinding
import com.example.ecomapp.domain.model.Product
import com.example.ecomapp.domain.repository.Products
import com.example.ecomapp.ui.model.ProductItemUiModel


class CartItemAdapter :
    RecyclerView.Adapter<CartItemAdapter.ProductViewHolder>() {
    private var productsList: List<Product> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(products: Products) {
        if (products != productsList) {
            productsList = products
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = RecyclerCartItemCardViewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.apply {
            bind(productsList[position])
        }

    }


    override fun getItemCount(): Int {
        return productsList.size
    }

    /*companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(ivProduct: ImageView, url: String) {
            Glide
                .with(ivProduct)
                .load(url)
                .error(R.drawable.placeholder_image)
                .into(ivProduct)
        }
    }*/


    inner class ProductViewHolder(val binding : RecyclerCartItemCardViewDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product

            if(product.discountPrice != null) {
                binding.productDiscountPrice.visibility = View.VISIBLE
                binding.productPrice.paintFlags = (binding.productPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)

            }
            else {
                binding.productDiscountPrice.visibility = View.GONE
                binding.productPrice.paintFlags = (binding.productPrice.paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG).inv())
            }
        }
    }
}