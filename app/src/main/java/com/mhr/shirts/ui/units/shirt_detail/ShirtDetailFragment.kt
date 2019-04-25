package com.mhr.shirts.ui.units.shirt_detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mhr.shirts.R

class ShirtDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ShirtDetailFragment()
    }

    private lateinit var viewModel: ShirtDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shirt_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShirtDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
