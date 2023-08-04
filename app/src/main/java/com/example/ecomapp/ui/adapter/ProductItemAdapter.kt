package com.example.ecomapp.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomapp.R
import com.example.ecomapp.ui.model.ProductItemUiModel


class ProductItemAdapter(private val productsList: List<ProductItemUiModel>) :
    RecyclerView.Adapter<ProductItemAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_card_view_design, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItemUi = productsList[position]

        holder.apply {
            tvProductTitle.text = productItemUi.title
            ivProductImage.setImageResource(productItemUi.imageResource)
            tvProductPrice.text = buildString {
                append("Price: $")
                append(productItemUi.price)
            }
            tvProductQuantity.text = buildString {
                append("Qty: ")
                append(productItemUi.quantity)
            }

            if(productItemUi.isPriceDiscounted) {
                tvProductDiscountPrice.text = buildString {
                    append("Discount: $")
                    append(productItemUi.discountPrice)
                }
                tvProductDiscountPrice.visibility = View.VISIBLE
                tvProductPrice.paintFlags = (holder.tvProductPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)

            }
            else {
                tvProductDiscountPrice.text = ""
                tvProductDiscountPrice.visibility = View.GONE
                tvProductPrice.paintFlags = (holder.tvProductPrice.paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG).inv())

            }
        }

    }


    override fun getItemCount(): Int {
        return productsList.size
    }



    inner class ProductViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvProductTitle: TextView = itemView.findViewById(R.id.tvProductTitle)
        val ivProductImage: ImageView = itemView.findViewById(R.id.productImage)
        val tvProductPrice: TextView = itemView.findViewById(R.id.productPrice)
        val tvProductQuantity: TextView = itemView.findViewById(R.id.tvProductQty)
        val tvProductDiscountPrice: TextView = itemView.findViewById(R.id.productDiscountPrice)

        init {
            itemView.findViewById<Button>(R.id.addToCartButton).setOnClickListener {
                Toast.makeText(it.context, tvProductPrice.text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}