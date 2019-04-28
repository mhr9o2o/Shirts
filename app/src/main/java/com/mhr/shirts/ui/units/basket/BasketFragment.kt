package com.mhr.shirts.ui.units.basket

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

import com.mhr.shirts.R
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

/**
 * BasketFragment is view to the basket unit.
 * It presents the basket items, shows errors or success messages as snack
 * and delivers ui interactions to the [BasketViewModel] and notifies it of unit creation.
 * Also implements BasketItemInteractorListener to catch the Basket List items interactions.
 */
class BasketFragment : Fragment(), BasketAdapter.BasketItemInteractionsListener {

    //region Fields
    companion object {
        fun newInstance() = BasketFragment()
    }

    private lateinit var viewModel: BasketViewModel
    private lateinit var disposables: CompositeDisposable
    private lateinit var shirts: MutableList<Shirt>
    private lateinit var adapter: BasketAdapter
    private lateinit var totalCostTextSchema: String

    //region Views
    private lateinit var rootView: View
    private lateinit var totalCostTextView: AppCompatTextView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    //endregion

    //endregion

    //region Functions
    private fun initViews()
    {
        totalCostTextView = rootView.findViewById(R.id.fragment_basket_total_cost_text_view)
        totalCostTextSchema = totalCostTextView.text.toString()
        val bottomSheetView: ViewGroup = rootView.parent as ViewGroup
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        initListeners()
        initList()
    }

    private fun initListeners()
    {
        rootView.findViewById<AppCompatButton>(R.id.fragment_basket_order_button).setOnClickListener {
            disposables.add(
                viewModel.onOrderClicked().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            showSuccessSnack()
                        },
                        {
                            showErrorSnack(it.message!!)
                        }
                    )
            )
        }

        rootView.findViewById<RelativeLayout>(R.id.fragment_basket_topbar_layout).setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
            {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            else if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
            {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

    }

    private fun showSuccessSnack()
    {
        val parent: ViewGroup = rootView.parent as ViewGroup
        val snackbar = Snackbar.make(parent, R.string.success_order, Snackbar.LENGTH_SHORT)
        var backgroundColor = ContextCompat.getColor(rootView.context, R.color.positive)
        snackbar.view.setBackgroundColor(backgroundColor)
        snackbar.show()
    }

    private fun showErrorSnack(message: String)
    {

        val parent: ViewGroup = rootView.parent as ViewGroup
        @StringRes val errorMessage: Int
        val length: Int

        if (message == DataAccessLayer.emptyBasketErrorMessage)
        {
            errorMessage = R.string.error_order_empty
            length = Snackbar.LENGTH_SHORT

            val snackbar = Snackbar.make(parent, errorMessage, length)
            val backgroundColor = ContextCompat.getColor(rootView.context, R.color.negative)
            snackbar.view.setBackgroundColor(backgroundColor)
            snackbar.show()

        }
        else
        {
            errorMessage = R.string.error_order
            length = Snackbar.LENGTH_INDEFINITE

            val snackbar = Snackbar.make(parent, errorMessage, length)
            val backgroundColor = ContextCompat.getColor(rootView.context, R.color.negative)
            val actionColor = ContextCompat.getColor(rootView.context, R.color.colorPrimary)
            snackbar.view.setBackgroundColor(backgroundColor)
            snackbar.setActionTextColor(actionColor)
            snackbar.setAction(R.string.try_again) {
                disposables.add(viewModel.onOrderClicked().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            showSuccessSnack()
                        },
                        {
                            showErrorSnack(it.message!!)
                        }
                    ))
                snackbar.dismiss()
            }
            snackbar.show()

        }
    }

    private fun initList()
    {
        shirts = mutableListOf()
        adapter = BasketAdapter(shirts, this)
        rootView.findViewById<RecyclerView>(R.id.fragment_basket_recycler_view).adapter = adapter
    }

    private fun adjustDataOnList(shirts: List<Shirt>)
    {
        this.shirts.clear()
        this.shirts.addAll(shirts)
        adapter.notifyDataSetChanged()
    }

    private fun updateTotalCost(cost: Int)
    {
        totalCostTextView.text = totalCostTextSchema.replace(oldValue = "%v", newValue = cost.toString())
    }

    private fun bind()
    {

        disposables = CompositeDisposable()

        disposables.add(
            viewModel.getBasketItems().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adjustDataOnList(it)
                }
        )

        disposables.add(
            viewModel.getTotalCost().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateTotalCost(it)
                }
        )

        disposables.add(viewModel.unitIsReady())

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        initViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BasketViewModel::class.java)
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

    //region Callbacks Implementation
    override fun onAddButtonClickedFor(shirt: Shirt) {
        viewModel.onShirtAdded(shirt)
    }

    override fun onRemoveButtonClickedFor(shirt: Shirt) {
        viewModel.onShirtRemoved(shirt)
    }

    override fun onDeleteButtonClickedFor(shirt: Shirt) {
        viewModel.onShirtDeleted(shirt)
    }
    //endregion

}
