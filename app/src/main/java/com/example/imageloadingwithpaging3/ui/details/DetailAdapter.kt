package com.example.imageloadingwithpaging3.ui.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.data.searchData.User
import com.example.imageloadingwithpaging3.databinding.CustomRowBinding
import com.example.imageloadingwithpaging3.databinding.CustomSavedRowBinding

class DetailAdapter: RecyclerView.Adapter<DetailAdapter.MyViewHolder>() {


    private var userList = emptyList<UnsplashPhoto>()
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomSavedRowBinding.bind( LayoutInflater.from(parent.context).inflate(R.layout.custom_saved_row, parent, false))

        return MyViewHolder(binding)
    }

    inner class MyViewHolder(private val binding: CustomSavedRowBinding) :
        RecyclerView.ViewHolder(binding.root){
            val firstNameCO = binding.firstNameTxt
            val textDescription = binding.textDescription
            val imageView2 = binding.imageView2

    }


    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.firstNameCO.text = currentItem.custom_name.toString()
        holder.textDescription.text = currentItem.description.toString()


        Glide.with(holder.itemView.context)
            .load(currentItem.custom_image)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_launcher_background)
            .into(holder.imageView2)

        holder.imageView2.setOnClickListener {
            Toast.makeText(holder.itemView.context, "" + currentItem.urls.small, Toast.LENGTH_SHORT).show()
        }



    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<UnsplashPhoto>){
        this.userList = user
        notifyDataSetChanged()
    }



}