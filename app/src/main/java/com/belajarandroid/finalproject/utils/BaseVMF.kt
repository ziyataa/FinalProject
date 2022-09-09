package com.belajarandroid.finalproject.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseVMF<VM : ViewModel> : ViewModelProvider.Factory {

    private var viewModel : VM? = null

    constructor(viewModel : VM) {
        this.viewModel = viewModel
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }

}