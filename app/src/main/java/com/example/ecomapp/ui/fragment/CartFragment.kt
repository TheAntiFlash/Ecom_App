package com.example.ecomapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecomapp.R
import com.example.ecomapp.databinding.FragmentCartBinding
import com.example.ecomapp.ui.adapter.CartItemAdapter
import com.example.ecomapp.ui.model.ProductItemUiModel
import com.example.ecomapp.vm.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private var data = mutableListOf<ProductItemUiModel>()
    private val viewModel by activityViewModels<ProductViewModel>()

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

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewLayout
        val adapter = CartItemAdapter()
        recyclerView.adapter = adapter
        viewModel.productsInCart.observe(viewLifecycleOwner) {
            Log.d("debug", it.size.toString())
            for(prod in it) {
                Log.d("debug", prod.name)
            }
            adapter.setProducts(it)
        }
    }

}