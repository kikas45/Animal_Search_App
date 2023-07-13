package com.example.imageloadingwithpaging3.ui.search.searchDataHistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.searchData.User
import com.example.imageloadingwithpaging3.databinding.CustomRowBinding

class ListAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    private var userList = emptyList<User>()
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.bind( LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))

        return MyViewHolder(binding)
    }

    interface OnItemClickListener{
        fun onItemClicked(photo: User)
    }


    inner class MyViewHolder(private val binding: CustomRowBinding) :
        RecyclerView.ViewHolder(binding.root){
            val firstNameCO = binding.firstNameTxt

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = userList.get(position)
                    if (item != null){
                        listener.onItemClicked(item)
                    }
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