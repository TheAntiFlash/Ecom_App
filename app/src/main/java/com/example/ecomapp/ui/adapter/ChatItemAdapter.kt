package com.example.ecomapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomapp.R
import com.example.ecomapp.ui.model.ChatProfileUiModel


class ChatItemAdapter(private val profilesList: List<ChatProfileUiModel>) :
    RecyclerView.Adapter<ChatItemAdapter.ProfileViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_card_chat_view_design, parent, false)

        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val chatProfileUi = profilesList[position]

        holder.apply {
            tvUsername.text = chatProfileUi.username
            ivProfileImage.setImageResource(chatProfileUi.imageResource)
            tvUserEmail.text = chatProfileUi.email
        }

    }


    override fun getItemCount(): Int {
        return profilesList.size
    }



    inner class ProfileViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val ivProfileImage: ImageView = itemView.findViewById(R.id.ivChatProfile)
        val tvUserEmail: TextView = itemView.findViewById(R.id.tvUserEmail)


    }
}