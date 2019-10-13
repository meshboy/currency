package com.ex.revolut.rate.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ex.revolut.R
import com.ex.revolut.core.data.rate.domain.RateModel
import com.ex.revolut.databinding.ContentRateListBinding

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-12
 */
class RateAdapter : ListAdapter<RateModel, RateAdapter.RateViewHolder>(DiffCallback) {

    private lateinit var onClickListener: OnClickListener
    private lateinit var onTextChangedListener: OnTextChanedListener

    fun setListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setTextChangeListener(onTextChangedListener: OnTextChanedListener) {
        this.onTextChangedListener = onTextChangedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rateModel = getItem(position)
        holder.bind(rateModel)

        holder.setListener {
            onClickListener.onClick(rateModel)

            val editText: EditText = it.findViewById(R.id.rateEditText)
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(character: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    onTextChangedListener.onChange(character)
                }
            })
        }
    }

    class RateViewHolder(val binding: ContentRateListBinding) :
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

    class OnTextChanedListener(val onTextChangedListener: (value: CharSequence?) -> Unit) {
        fun onChange(value: CharSequence?) = onTextChangedListener(value)
    }

    class OnClickListener(val clickListener: (RateModel: RateModel) -> Unit) {
        fun onClick(rateModel: RateModel) = clickListener(rateModel)
    }
}