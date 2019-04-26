package com.mhr.shirts.ui.units.shirts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.mhr.shirts.R
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShirtsFragment : Fragment() {

    //region Fields
    companion object {
        fun newInstance() = ShirtsFragment()
    }

    private lateinit var viewModel: ShirtsViewModel
    private val disposables = CompositeDisposable()
    private lateinit var shirts: MutableList<Shirt>
    private lateinit var adapter: ShirtsAdapter
    //endregion

    //region Functions
    private fun bind()
    {
        disposables.add(
            viewModel.getShirts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateList(it)
                }
        )

        disposables.add(
            viewModel.getFilteredShirts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateList(it)
                }
        )

    }

    private fun unBind()
    {
        disposables.clear()
    }

    private fun initViews(rootView: View)
    {
        shirts = mutableListOf()
        adapter = ShirtsAdapter(shirts)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.fragment_shirts_recycler_view)
        recyclerView.adapter = adapter
    }

    private fun updateList(newList: List<Shirt>)
    {
        shirts.clear()
        shirts.addAll(newList)
        adapter.notifyDataSetChanged()
    }
    //endregion

    //region Overridden Functions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shirts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShirtsViewModel::class.java)
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
