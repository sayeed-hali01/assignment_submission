package com.example.landmark.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.example.Data
import com.example.landmark.ConstantsLM
import com.example.landmark.activity.PhotoZooom
import com.example.landmark.databinding.ItemProductBinding
import com.example.landmark.entity.NewsData
import kotlin.collections.ArrayList

class ProductGridAdapter( var newsData: NewsData, private val context:Context): RecyclerView.Adapter<ProductGridAdapter.ViewHolder>() {

    private var dataList: ArrayList<Data>?

    init {
        dataList = newsData.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(dataList?.get(position)?.imageUrl)
            .into(holder.binding.ivProd)

        holder.binding.ivProd.setOnClickListener {
            val intent = Intent(context, PhotoZooom::class.java)
            intent.putExtra(ConstantsLM.PHOTO_URL, dataList?.get(position)?.imageUrl)
            intent.putExtra(ConstantsLM.NEWS_CONTENT,dataList?.get(position)?.description)
            context.startActivity(intent)
        }
    }
    fun addItems(moreData: NewsData?) {
        val initialSize = itemCount
        moreData?.let { dataList?.addAll(it.data) }
        dataList?.let { notifyItemRangeInserted(initialSize, it.size) }
        if (moreData != null) {
            newsData.meta = moreData.meta
        }
    }
    class ViewHolder(val binding: ItemProductBinding):RecyclerView.ViewHolder(binding.root)
}