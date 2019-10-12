package com.ex.revolut.rate.adapter

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ex.revolut.R
import com.ex.revolut.core.data.rate.domain.RateModel
import java.util.*

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-12
 */
@BindingAdapter("currency")
fun TextView.setCurrency(model: RateModel) {
    text = model.currency
}

@BindingAdapter("currencyDesc")
fun TextView.setCurrencyDesc(model: RateModel) {
    text = getCurrencyDesc(model.currency)
}

@BindingAdapter("rate")
fun EditText.setRate(model: RateModel) {
    setText("${model.rate}")
}

@BindingAdapter("currencyImage")
fun ImageView.setImage(model: RateModel) {
    setImageResource(
        when (model.currency) {
            "USD" -> R.drawable.ic_us
            "EUR" -> R.drawable.ic_eu
            "SEK" -> R.drawable.ic_sweden
            "CAD" -> R.drawable.ic_canada
            else -> R.drawable.default_currency
        }
    )
}

//@BindingAdapter("rateDataList")
//fun RecyclerView.setRateDataList(data: List<RateModel>?) {
//    data?.let {
//        val adapter = adapter as RateAdapter
////        adapter.onClickListener
//        adapter.submitList(it)
//    }
//}


fun getCurrencyDesc(currency: String): String {
    val currencyInstance = Currency.getInstance(currency)
    return currencyInstance.displayName
}