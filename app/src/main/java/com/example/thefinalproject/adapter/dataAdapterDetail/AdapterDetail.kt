package com.example.thefinalproject.adapter.dataAdapterDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.databinding.ItemmateriContentchapterBinding
import com.example.thefinalproject.databinding.ItemmateriTitlechapterBinding
import com.example.thefinalproject.network.model.chapters.DataChapters1
import com.example.thefinalproject.network.model.chapters.DataChaptersNew
import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.ModuleById


class AdapterDetail(private val data: List<Any>,private var clickListener: ((String) -> Unit)? = null): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ChapterById -> ITEM_CHAPTER
            is ModuleById -> ITEM_MODULE
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    inner class ChapterViewHolder(private val binding: ItemmateriTitlechapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ChapterById) {
            binding.tvNoChapter.text = "Chapter - ${data.chapterNumber}"
            binding.tvNamaChapter.text = data.title
            binding.tvDurasi.text = "${data.duration} Menit"

        }
    }
    inner class ModuleViewHolder(private val binding: ItemmateriContentchapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.constraint.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val clickedItem = data[position]
                    if (clickedItem is ModuleById) {
                        clickListener?.invoke(clickedItem.id)
                    }
                }
            }
        }
        fun onBind(data: ModuleById) {
            binding.tvNamaContentChapter.text = data.title

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_CHAPTER -> {
                val binding =
                    ItemmateriTitlechapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChapterViewHolder(binding)
            }

            ITEM_MODULE -> {
                val binding =
                    ItemmateriContentchapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ModuleViewHolder(binding)
            }

            else -> throw throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_CHAPTER -> {
                val headerHolder = holder as ChapterViewHolder
                headerHolder.onBind(data[position] as ChapterById)
            }

            ITEM_MODULE -> {
                val moduleHolder = holder as ModuleViewHolder
                val listenerItem = (data[position] as ModuleById)
                moduleHolder.onBind(listenerItem)
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }
    }


    companion object {
        private const val ITEM_CHAPTER = 0
        private const val ITEM_MODULE = 1
    }
}