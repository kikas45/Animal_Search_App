package com.example.imageloadingwithpaging3.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.navArgument
import com.example.imageloadingwithpaging3.R

class DetailsFragment : Fragment(R.layout.fragment_details) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val urls = arguments?.getString("urls")
        val user = arguments?.getString("user")
        val description = arguments?.getString("description")
        val id = arguments?.getString("id")

        Toast.makeText(context, "" + description, Toast.LENGTH_SHORT).show()

    }

}