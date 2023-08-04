package com.example.ecomapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.ecomapp.R
import com.example.ecomapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genders =  resources.getStringArray(R.array.genders)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.gender_drop_down_menu_item, genders)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }
}