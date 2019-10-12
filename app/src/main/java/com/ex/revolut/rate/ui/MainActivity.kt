package com.ex.revolut.rate.ui

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ex.revolut.R
import com.ex.revolut.core.hide
import com.ex.revolut.core.mvvm.BaseActivity
import com.ex.revolut.core.show
import com.ex.revolut.databinding.ActivityMainBinding
import com.ex.revolut.rate.adapter.RateAdapter
import com.ex.revolut.rate.view.MainView
import com.ex.revolut.rate.viewmodel.MainViewModel
import com.ex.revolut.rate.viewmodel.MainViewModelFactory
import org.kodein.di.generic.instance
import timber.log.Timber

class MainActivity : BaseActivity<MainView>(), MainView {

    override fun createView(): MainView = this

    lateinit var binding: ActivityMainBinding

    private val viewModelFactory: MainViewModelFactory by instance()

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

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
        adapter.setListener(RateAdapter.OnClickListener {rate ->
            Timber.d("selected rate %s", rate)
            viewModel.saveBaseCurrency(rate.currency)
//            TODO: fetch the rate based on the current row, ensure the selected row is at the top of the list
        })
        binding.recyclerView.adapter = adapter

        viewModel.rates.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        })
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
