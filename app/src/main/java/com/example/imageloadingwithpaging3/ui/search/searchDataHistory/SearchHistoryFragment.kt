package com.example.imageloadingwithpaging3.ui.search.searchDataHistory

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.searchData.User
import com.example.imageloadingwithpaging3.data.searchData.UserViewModel
import com.example.imageloadingwithpaging3.databinding.FragmentSearchHistoryBinding


class SearchHistoryFragment : Fragment(R.layout.fragment_search_history),
    ListAdapter.OnItemClickListener, ListAdapter.OnItemLongClickListener {

    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.bind(view)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        showKeyBoard()

        DisplayMySearch()

        binding.btnDeleteall.setOnClickListener {
            deleteAllUsers()

        }

        binding.apply {

            backArrowPre.setOnClickListener {
                view.findNavController().popBackStack(R.id.galleryFragment, false)
                hideKeyBaord()

            }

            val bundle = Bundle()


            val sharedDatasss = view.context.applicationContext.getSharedPreferences(
                "PASS_DATA_TRANS_FRAGMENT",
                Context.MODE_PRIVATE
            )
            val editor = sharedDatasss.edit()

            editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val editUrl = binding.editText.text.toString().trim()
                    hideKeyBaord()

                    if (!editUrl.isEmpty()) {
                        insertDataToDatabase(editUrl)
                        bundle.putString("search", editUrl)
                        editor.putString("search", "SavedData")
                        editor.apply()
                        view.findNavController()
                            .navigate(R.id.action_searchHistoryFragment_to_searchFragment, bundle)


                    } else {
                        Toast.makeText(context, "Input text for search", Toast.LENGTH_SHORT).show()
                    }
                    return@OnEditorActionListener true
                }
                false
            })


        }



        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length > 0) {
                    binding.editClear.visibility = View.VISIBLE
                } else {
                    binding.editClear.visibility = View.INVISIBLE
                }
            }
        })

        binding.editClear.setOnClickListener { v -> binding.editText.text.clear() }


    }


    private fun insertDataToDatabase(name: String) {

        val user = User(name)
        // Add Data to Database
        mUserViewModel.addUser(user)

    }

    private fun showKeyBoard() {
        try {
            binding.editText.requestFocus()
            val imm =
                requireContext().applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        } catch (ignored: Exception) {
        }
    }

    private fun hideKeyBaord() {
        try {
            val imm =
                requireContext().applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.editText.windowToken, 0)
        } catch (ignored: Exception) {
        }
    }


    private fun DisplayMySearch() {
        // Recyclerview
        val adapter = ListAdapter(this, this)


        binding.apply {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())

        }
        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)

        })

    }


    override fun onResume() {
        super.onResume()

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.findNavController()?.popBackStack(R.id.galleryFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

    }

    override fun onItemClicked(_user: User) {
        val name = _user.firstName.toString()

        /// val user = User(name)
        //  mUserViewModel.deleteUser(user)

        val sharedDatasss = view?.context?.applicationContext?.getSharedPreferences(
            "PASS_DATA_TRANS_FRAGMENT",
            Context.MODE_PRIVATE
        )
        val editor = sharedDatasss?.edit()

        val bundle = Bundle().apply { putString("search", name) }

        editor?.putString("search", "SavedData")
        editor?.apply()

        view?.findNavController()
            ?.navigate(R.id.action_searchHistoryFragment_to_searchFragment, bundle)
        hideKeyBaord()

    }

    override fun onItemLongClicked(photo: User) {

        val name = photo.firstName.toString()
        val user = User(name)

        deleteUser(user)
        hideKeyBaord()



    }


    private fun deleteUser(photo: User) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(photo)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${photo.firstName.toString()}",
                Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${photo.firstName.toString()}?")
        builder.setMessage("Are you sure you want to delete ${photo.firstName.toString()}?")
        builder.create().show()
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

}