package com.example.thefinalproject.ui.fragment.itemPage.homepage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.foritemhomepage.AdapterAllKursusPopuler
import com.example.thefinalproject.databinding.FragmentItemSemuaKelasBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.CategoryResponse
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemForProductm.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemForProductm : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentItemSemuaKelasBinding
    private val viewMode : ViewModelAll by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemSemuaKelasBinding.inflate(inflater, container, false)
        fetchList()
        return binding.root
    }


    private fun fetchList() {

        viewMode.getFilter("null", "Product Management", "null").observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showListHorizontal(it.data)
                    binding.pgbarAll.isVisible = false

                }

                Status.ERROR -> {
                    binding.pgbarAll.isVisible = false
                    Log.e("Errorr", it.message.toString())
                }

                Status.LOADING -> {
                    binding.pgbarAll.isVisible = true
                }
            }


        }
    }


    private fun showListHorizontal(data: CategoryResponse?) {
        data?.data?.let {
            val adapter = AdapterAllKursusPopuler(onButtonClick = {
                findNavController().navigate(R.id.action_homeFragment2_to_detailPaymentFragment)

            })




        adapter.sendList(it)
        binding.rvHomeAllCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeAllCategory.adapter = adapter
    }
    }


}

