package com.example.imageloadingwithpaging3.ui.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.databinding.CustomSavedRowBinding

class SavedDetailAdapter(private val listener:OnItemClickListenerDetails ): RecyclerView.Adapter<SavedDetailAdapter.MyViewHolder>() {


    private var userList = emptyList<UnsplashPhoto>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomSavedRowBinding.bind( LayoutInflater.from(parent.context).inflate(R.layout.custom_saved_row, parent, false))

        return MyViewHolder(binding)
    }

    interface OnItemClickListenerDetails {
        fun onclickDetailsItem(photo: UnsplashPhoto)

    }


    inner class MyViewHolder(private val binding: CustomSavedRowBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = userList[position]
                    if (item != null){
                        listener.onclickDetailsItem(item)
                    }
                }

            }
        }




        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.custom_image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView2)

                firstNameTxt.text = photo.custom_name
                textDescription.text = photo.description
            }
        }

    }



    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<UnsplashPhoto>){
        this.userList = user
        notifyDataSetChanged()
    }



}