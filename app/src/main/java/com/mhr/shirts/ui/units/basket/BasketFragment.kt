package com.mhr.shirts.ui.units.basket

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mhr.shirts.R
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.disposables.CompositeDisposable

class BasketFragment : Fragment() {

    //region Fields
    companion object {
        fun newInstance() = BasketFragment()
    }

    private lateinit var viewModel: BasketViewModel
    private val disposables = CompositeDisposable()
    private lateinit var shirts: MutableList<Shirt>
    //region Views
    //endregion

    //endregion

    //region Functions
    private fun initViews()
    {

    }

    private fun initListeners()
    {

    }

    private fun initList()
    {

    }

    private fun adjustDataOnList(shirts: List<Shirt>)
    {

    }

    private fun bind()
    {

    }

    private fun unBind()
    {
        disposables.clear()
    }
    //endregion

    //region Overridden Functions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BasketViewModel::class.java)
        disposables.add(viewModel.unitIsReady())
    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    override fun onPause() {
        super.onPause()
        unBind()
    }
    //endregion

}
