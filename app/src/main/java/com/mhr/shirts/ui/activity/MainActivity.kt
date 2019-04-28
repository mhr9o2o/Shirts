package com.mhr.shirts.ui.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.mhr.shirts.MyApplication
import com.mhr.shirts.R
import com.mhr.shirts.data.DataAccessLayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    //region Fields
    @Inject
    lateinit var dataAccessLayer: DataAccessLayer
    var bottomSheetState: Int = BottomSheetBehavior.STATE_COLLAPSED
    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    lateinit var rootNavController: NavController
    val disposables = CompositeDisposable()
    //endregion

    //region Overridden Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initData()

    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    override fun onStop() {
        super.onStop()
        dataAccessLayer.dispose()
        unBind()
    }

    override fun onBackPressed() {
        if (bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED)
        {
            if (!rootNavController.navigateUp())
            {
                super.onBackPressed()
            }
        }
        else
        {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }
    //endregion

    //region Functions
    private fun initViews()
    {

        rootNavController = Navigation.findNavController(this, R.id.activity_main_root_fragment)

        val bottomSheetView: View = findViewById(R.id.activity_main_bottom_sheet_fragment)
        val rootView: View = findViewById(R.id.activity_main_root_fragment)
        val behavior = BottomSheetBehavior.from(bottomSheetView)

        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                bottomSheetState = newState
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                rootView.alpha = 1f - slideOffset
            }
        })

        bottomSheetBehavior = behavior
    }

    private fun initData()
    {
        MyApplication.instance.dataComponent.inject(this)
    }

    private fun bind()
    {
        disposables.add(dataAccessLayer.errors.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showGeneralErrorSnack()
            })
    }

    private fun unBind()
    {
        disposables.clear()
    }

    private fun showGeneralErrorSnack()
    {
        val snackbar = Snackbar.make(activity_main_root_view, R.string.error_general, Snackbar.LENGTH_INDEFINITE)
        var backgroundColor = ContextCompat.getColor(this, R.color.negative)
        var actionColor = ContextCompat.getColor(this, R.color.colorPrimary)
        snackbar.view.setBackgroundColor(backgroundColor)
        snackbar.setActionTextColor(actionColor)
        snackbar.setAction(R.string.try_again) {
            dataAccessLayer.fetchShirts()
            snackbar.dismiss()
        }
        snackbar.show()
    }
    //endregion
}
