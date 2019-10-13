package com.ex.revolut.rate.ui

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ex.revolut.R
import com.ex.revolut.core.data.rate.domain.RateModel
import com.ex.revolut.core.hide
import com.ex.revolut.core.mvvm.BaseActivity
import com.ex.revolut.core.show
import com.ex.revolut.databinding.ActivityMainBinding
import com.ex.revolut.rate.adapter.RateAdapter
import com.ex.revolut.rate.view.MainView
import com.ex.revolut.rate.viewmodel.MainViewModel
import com.ex.revolut.rate.viewmodel.MainViewModelFactory
import org.kodein.di.generic.instance
import java.text.NumberFormat
import java.util.*

class MainActivity : BaseActivity<MainView>(), MainView {

    override fun createView(): MainView = this

    lateinit var binding: ActivityMainBinding

    private val viewModelFactory: MainViewModelFactory by instance()

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    var selectedRate: RateModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.view = createView()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.baseCurrency.observe(this, Observer {
            if (it != null) {
                viewModel.fetchRates(it.recentBase)
            }
        })

        val adapter = RateAdapter()
        adapter.setListener(RateAdapter.OnClickListener { rate ->
            selectedRate = rate
            viewModel.saveBaseCurrency(rate.currency)
        })

//        list to text change on the selected editText to adjust the rate based on the typed value
        adapter.setTextChangeListener(RateAdapter.OnTextChanedListener { typedValue ->

            for (i in 1..binding.recyclerView.childCount) {

                val rateModel: RateModel = adapter.currentList[i]

                val childHolder = binding.recyclerView.findViewHolderForLayoutPosition(i)
                if (childHolder is RateAdapter.RateViewHolder) {
                    val editText = childHolder.binding.rateEditText

                    typedValue?.let { currency ->
                        if (currency.isNotEmpty()) {
                            val calculatedRate =
                                currency.toString().replace(",", "").toDouble() / rateModel.rate
                            editText.setText(
                                NumberFormat.getNumberInstance(Locale.US).format(
                                    String.format(
                                        "%.3f",
                                        calculatedRate
                                    ).toDouble()
                                )
                            )
                        }
                    }
                }
            }
        })

        binding.recyclerView.adapter = adapter

        viewModel.rates.observe(this, Observer { list ->
            if (!list.isNullOrEmpty()) {
                val mutableList = list.toMutableList()
                if (selectedRate != null) {
                    mutableList.add(0, selectedRate!!)
                }
                adapter.submitList(mutableList)
            }
        })

//        download and update every 1 sec
        val timer = Timer()
        val task = object : TimerTask() {

            override fun run() = viewModel.downloadAndUpdate()
        }
        timer.schedule(task, 0, 1000)
    }

    override fun showError(error: String?) {
        error?.let {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        binding.progressBar.show()
        binding.recyclerView.hide()
    }

    override fun hideLoading() {
        binding.progressBar.hide()
        binding.recyclerView.show()
    }
}
