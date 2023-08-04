package com.example.ecomapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecomapp.R
import com.example.ecomapp.databinding.FragmentHomeBinding
import com.example.ecomapp.ui.adapter.ProductItemAdapter
import com.example.ecomapp.ui.model.ProductItemUiModel
import com.example.ecomapp.vm.ProductViewModel


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private var data = mutableListOf<ProductItemUiModel>()
    private val viewModel by viewModels<ProductViewModel>()



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
        val adapter = ProductItemAdapter(data)
        recyclerView.adapter = adapter
    }
}