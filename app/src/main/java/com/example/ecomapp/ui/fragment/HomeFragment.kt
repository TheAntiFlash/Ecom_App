package com.example.ecomapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.ecomapp.domain.model.Response.Success
import com.example.ecomapp.domain.model.Response.Failure
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecomapp.R
import com.example.ecomapp.databinding.FragmentHomeBinding
import com.example.ecomapp.ui.adapter.ProductItemAdapter
import com.example.ecomapp.ui.model.ProductItemUiModel
import com.example.ecomapp.vm.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private var data = mutableListOf<ProductItemUiModel>()
    private val viewModel by activityViewModels<ProductViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        viewModel.loadProducts()
        val recyclerView = binding.recyclerViewLayout
        recyclerView.layoutManager = GridLayoutManager(context,2)
        val adapter = ProductItemAdapter(viewModel.addToCart)
        recyclerView.adapter = adapter

        viewModel.listOfProducts.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> adapter.setProducts(it.data)
                is Failure -> Toast.makeText(activity, "Error Loading Products", Toast.LENGTH_LONG).show()
                else -> Unit
            }
        }

    }
}