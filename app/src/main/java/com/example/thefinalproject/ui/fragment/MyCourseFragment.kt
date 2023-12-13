package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterPageFragment
import com.example.thefinalproject.adapter.CourseAdapter
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCategory

import com.example.thefinalproject.ui.fragment.itemPage.course.KelasFree
import com.example.thefinalproject.ui.fragment.itemPage.course.KelasPremium
import com.example.thefinalproject.ui.fragment.itemPage.course.SemuaKelasCourseFragment
import com.example.thefinalproject.util.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class MyCourseFragment : Fragment() {

    private var _binding: FragmentMyCourseBinding? = null
    private val binding get() = _binding!!

    private val viewMode : ViewModelAll by inject()
    private var categorys: List<DataCategory> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragmen
        _binding = FragmentMyCourseBinding.inflate(layoutInflater,container,false)
        val fragmentList = arrayListOf(SemuaKelasCourseFragment(), KelasPremium(), KelasFree())
        val titleFragment = arrayListOf("Semua Kelas", "Premium", "Free")
        binding.apply {
            viewpageCourse.adapter = AdapterPageFragment(fragmentList, requireActivity().supportFragmentManager, lifecycle)
            TabLayoutMediator(tabLayoutKursus, viewpageCourse) { tab, position ->
                tab.text = titleFragment[position]
            }.attach()
        }

        return binding.root
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchList(null,null,null,null,null)
    }

    private fun fetchList(id: String?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(id, level,category, type, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    categorys = it.data?.data ?: emptyList()

                    showListHorizontal(it.data)


                    binding.progressbarCourse.isVisible = false

                }

                Status.ERROR -> {
                    binding.progressbarCourse.isVisible = false
                    Log.e("Errorr", it.message.toString())
                }

                Status.LOADING -> {
                    binding.progressbarCourse.isVisible = true
                }
            }


        }
    }
    private fun showBottomSheetDialog() {
        val bsDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.botsheet_filtering_class, null)


        bsDialog.setContentView(bottomSheetView)
        bsDialog.show()



    }
    private fun showListHorizontal(data: ListResponse?) {


        val adapter = CourseAdapter()

        val uniqueType = data?.data?.distinctBy { it.type }
        adapter.sendList(data?.data ?: emptyList())

        binding.rvCourse.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvCourse.adapter = adapter


    }





     */
}

