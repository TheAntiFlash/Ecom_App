package com.example.ecomapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecomapp.R
import com.example.ecomapp.databinding.FragmentCartBinding
import com.example.ecomapp.ui.adapter.CartItemAdapter
import com.example.ecomapp.ui.model.ProductItemUiModel

class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private var data = mutableListOf<ProductItemUiModel>()

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewLayout
        recyclerView.layoutManager = GridLayoutManager(context,2)
        for( i in 1..10) {
            data.add(
                ProductItemUiModel(R.drawable.placeholder_image, "item $i", i*100, i*2 , i*80, true)
            )
        }
        for(i in 11..20) {
            data.add(
                ProductItemUiModel(R.drawable.placeholder_image, "item $i", i*100, i*2 , 0, false)
            )
        }
        val adapter = CartItemAdapter(data)
        recyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

}