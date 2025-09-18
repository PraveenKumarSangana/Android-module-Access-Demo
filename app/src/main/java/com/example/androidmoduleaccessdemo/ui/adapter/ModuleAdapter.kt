package com.example.androidmoduleaccessdemo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoduleaccessdemo.databinding.ItemCommonCardViewLayoutBinding
import com.example.androidmoduleaccessdemo.model.Module

class ModuleAdapter(
    private val modulesList: ArrayList<Module>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommonCardViewLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Module = modulesList[position]
        holder.setData(data)
    }

    override fun getItemCount(): Int {
        return modulesList.size
    }

    inner class ViewHolder(private val binding: ItemCommonCardViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(data: Module) {
            binding.tvModuleName.text = data.title
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(data)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(data: Module)
    }

}