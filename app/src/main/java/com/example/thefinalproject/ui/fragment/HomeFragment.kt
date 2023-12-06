package com.example.thefinalproject.ui.fragment

import android.media.RouteListingPreference
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.foritemhomepage.AdapterHomePage
import com.example.thefinalproject.databinding.FragmentHomeBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.CategoryResponse
import com.example.thefinalproject.network.model.DataCategory
import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewMode : ViewModelAll by inject()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapt: AdapterHomePage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)


        fetchCategory(null)


        tabLayout = binding.tabLayoutKursus
        viewPager2 = binding.viewpageKursus
        adapt= AdapterHomePage(this)
        viewPager2.setOnTouchListener { _, _ -> true  }
        viewPager2.adapter = adapt
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Semua Kelas"
                1 -> tab.text = "Product Management"
                2 -> tab.text = "Web Development"
                3 -> tab.text = "UI/UX Design"
            }
        }.attach()
        return binding.root

    }

    private fun fetchCategory(category: String?) {
        viewMode.getAllCategory(category).observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS -> {
                    showCategory(it.data)
                    //tablayout(it.data)
                    binding.progressbarCategory.isVisible = false

                }
                Status.ERROR -> {
                    binding.progressbarCategory.isVisible = false
                    Log.e("ERROR", it.message.toString())
                }
                Status.LOADING -> {
                    binding.progressbarCategory.isVisible = true
                }
            }
        }
    }

    private fun showCategory(data: CategoryResponse?){
        val adapter = AdapterCategory(object : AdapterCategory.OnClickListener{
            override fun itemClick(data: DataCategory) {
                navigatoToCourse(data)
            }


        })
        val uniqueCategories = data?.data?.distinctBy { it.category }

        adapter.sendCategory(uniqueCategories ?: emptyList())
        binding.recycleviewCategory.layoutManager= GridLayoutManager(requireActivity(), 2)
        binding.recycleviewCategory.adapter = adapter
    }

    private fun navigatoToCourse(data: DataCategory){

        val bundle = bundleOf("key" to data)
        findNavController().navigate(R.id.action_homeFragment2_to_myCourseFragment2,bundle)
    }

}