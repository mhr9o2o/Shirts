package com.mhr.shirts.ui.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
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

/**
 * This is the beginning of the UI activities
 * MainActivity contains our navigation controllers in order to handle back-stack
 * Errors are generally handled here [except order-related errors] so MainActivity also has access to DataAccessLayer
 */
class MainActivity : AppCompatActivity() {

    //region Fields
    @Inject
    lateinit var dataAccessLayer: DataAccessLayer
    private var bottomSheetState: Int = BottomSheetBehavior.STATE_COLLAPSED
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private lateinit var rootNavController: NavController
    private var snackbar: Snackbar? = null
    private val disposables = CompositeDisposable()
    //endregion

    //region Overridden Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initData()

    }

    /**
     * Subscribes to disposables whenever the activity is resumed
     */
    override fun onResume() {
        super.onResume()
        bind()
    }

    /**
     * Unsubscribes whenever the activity is paused
     */
    override fun onPause() {
        super.onPause()
        dataAccessLayer.dispose()
        unBind()
    }

    /**
     * Closes the data base when activity is destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        dataAccessLayer.closeDataBase()
    }

    /**
     * Checks if Basket bottom-sheet is open to collapse it firstly,
     * then checks if root-navigation has any back-stack to navigate it up, and finally works as default [closes the app in our case]
     */
    override fun onBackPressed() {
        if (bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED) {
            if (!rootNavController.navigateUp()) {
                super.onBackPressed()
            }
        } else {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }
    //endregion

    //region Functions
    /**
     * Views and required listeners are being initialised here
     */
    private fun initViews() {

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

    /**
     * Provides the DataAccessLayer by injecting it when the activity is created
     */
    private fun initData() {
        MyApplication.instance.dataComponent.inject(this)
    }

    /**
     * Subscribes to subjects/observables
     */
    private fun bind() {
        disposables.add(dataAccessLayer.errors.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showGeneralErrorSnack(it)
            })

        disposables.add(dataAccessLayer.shirts.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                snackbar?.dismiss()
            })
    }

    /**
     * Unsubscribes subjects/observables
     */
    private fun unBind() {
        disposables.clear()
    }

    /**
     * Creates and shows an error snack according to the given error message
     * @param message Error message provided by DataAccessLayer
     */
    private fun showGeneralErrorSnack(message: String) {
        @StringRes val snackMessage = if (message == DataAccessLayer.networkErrorMessage) {
            R.string.error_network
        } else {
            R.string.error_general
        }
        snackbar = Snackbar.make(activity_main_root_view, snackMessage, Snackbar.LENGTH_INDEFINITE)
        var backgroundColor = ContextCompat.getColor(this, R.color.negative)
        var actionColor = ContextCompat.getColor(this, R.color.colorPrimary)
        snackbar!!.view.setBackgroundColor(backgroundColor)
        snackbar!!.setActionTextColor(actionColor)
        snackbar!!.setAction(R.string.try_again) {
            dataAccessLayer.fetchShirts()
            snackbar!!.dismiss()
        }
        snackbar!!.show()
    }
    //endregion
}
