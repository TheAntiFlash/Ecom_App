package com.example.ecomapp.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomapp.R
import com.example.ecomapp.databinding.RecyclerCardViewDesignBinding
import com.example.ecomapp.domain.model.Product
import com.example.ecomapp.domain.repository.Products
import com.example.ecomapp.ui.model.ProductItemUiModel
import com.google.common.io.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job


class ProductItemAdapter(private val addToCart: (id: String) -> Job) :
    RecyclerView.Adapter<ProductItemAdapter.ProductViewHolder>() {
    private var productsList: List<Product> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(products: Products) {
        if (products != productsList) {
            productsList = products
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = RecyclerCardViewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view, addToCart)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.apply {
            bind(productsList[position])

        }
    }


    override fun getItemCount(): Int {
        return productsList.size
    }
    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(ivProduct: ImageView, url: String) {
            Glide
                .with(ivProduct)
                .load(url)
                .error(R.drawable.placeholder_image)
                .into(ivProduct)
        }
    }



    inner class ProductViewHolder(val binding : RecyclerCardViewDesignBinding, val addToCart: (id: String) -> Job) : RecyclerView.ViewHolder(binding.root) {
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

        init {
            binding.addToCartButton.setOnClickListener {
                Toast.makeText(it.context, binding.product?.id, Toast.LENGTH_SHORT).show()

                binding.product?.id?.let { id -> addToCart(id) }
            }
        }
    }
}