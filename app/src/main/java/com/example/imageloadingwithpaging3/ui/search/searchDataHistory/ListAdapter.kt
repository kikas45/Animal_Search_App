package com.example.imageloadingwithpaging3.ui.search.searchDataHistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.searchData.User
import com.example.imageloadingwithpaging3.databinding.CustomRowBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    private var userList = emptyList<User>()
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.bind( LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))

        return MyViewHolder(binding)
    }

    inner class MyViewHolder(private val binding: CustomRowBinding) :
        RecyclerView.ViewHolder(binding.root){
            val firstNameCO = binding.firstNameTxt

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){

                    Toast.makeText(itemView.context, "Yes ", Toast.LENGTH_SHORT).show()
                    
                    }
                }

            }

    }


    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.firstNameCO.text = currentItem.firstName
        
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }



}