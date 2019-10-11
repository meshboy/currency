package com.ex.revolut.rate.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.ex.revolut.R
import com.ex.revolut.core.mvvm.BaseActivity
import com.ex.revolut.rate.view.MainView
import com.ex.revolut.rate.viewmodel.MainViewModel
import com.ex.revolut.rate.viewmodel.MainViewModelFactory
import com.ex.revolut.databinding.ActivityMainBinding
import org.kodein.di.generic.instance

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
    }

    override fun showError(error: String?) {

    }
}
