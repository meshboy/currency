package com.ex.revolut.rate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ex.revolut.core.data.rate.domain.RateModel
import com.ex.revolut.databinding.ContentRateListBinding

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-12
 */
class RateAdapter  : ListAdapter<RateModel, RateAdapter.RateViewHolder>(DiffCallback) {

    lateinit var onClickListener: OnClickListener

    fun setListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rateModel = getItem(position)
        holder.bind(rateModel)

        holder.setListener {
            onClickListener.onClick(rateModel)
        }
    }

    class RateViewHolder(private val binding: ContentRateListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(RateModel: RateModel) {
            binding.model = RateModel
            binding.executePendingBindings()
        }

        fun setListener(listener: (v: View) -> Unit) {
            binding.cardLayout.setOnClickListener(listener)
        }

        companion object {
            fun from(parent: ViewGroup): RateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ContentRateListBinding.inflate(layoutInflater, parent, false)
                return RateViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [RateModel]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<RateModel>() {
        override fun areItemsTheSame(oldItem: RateModel, newItem: RateModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RateModel, newItem: RateModel): Boolean {
            return oldItem.currency == newItem.currency
        }
    }

    class OnClickListener(val clickListener: (RateModel: RateModel) -> Unit) {
        fun onClick(RateModel: RateModel) = clickListener(RateModel)
    }
}