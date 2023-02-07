package com.example.registretionhome.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.registretionhome.R
import com.example.registretionhome.databinding.CardItemBinding
import com.example.registretionhome.model.UserData

class UserAdapter(val list: ArrayList<UserData>,val listener : ClickListenerItems) : RecyclerView.Adapter<UserAdapter.Holder>() {

    inner class Holder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userData: UserData) {
            binding.tvUserFullname.text = userData.fullname
            binding.tvtelUser.text = userData.telnumber
            binding.profileImage.setImageBitmap(userData.bitmap)

            itemView.setOnClickListener {
                listener.clickitem(userData)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.binding.root.context, R.anim.recycleranim))

        holder.bind(list[position])
    }


    interface  ClickListenerItems{

        fun clickitem(userData: UserData)
    }
}