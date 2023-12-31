package com.example.ecomapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomapp.R
import com.example.ecomapp.databinding.FragmentChatBinding
import com.example.ecomapp.ui.adapter.ChatItemAdapter
import com.example.ecomapp.ui.model.ChatProfileUiModel


class ChatFragment : Fragment() {

    private lateinit var binding : FragmentChatBinding
    private var data = mutableListOf<ChatProfileUiModel>()

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewLayout
        recyclerView.layoutManager = LinearLayoutManager(context)
        for( i in 1..10) {
            data.add(
                ChatProfileUiModel("User $i", "user$i@ecomapp.com", R.drawable.baseline_person_24)
            )
        }

        val adapter = ChatItemAdapter(data)
        recyclerView.adapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

}