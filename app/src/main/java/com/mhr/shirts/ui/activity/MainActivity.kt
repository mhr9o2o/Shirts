package com.mhr.shirts.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mhr.shirts.MyApplication
import com.mhr.shirts.R
import com.mhr.shirts.data.DataAccessLayer
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    //region Fields
    @Inject
    lateinit var dataAccessLayer: DataAccessLayer
    var bottomSheetState: Int = BottomSheetBehavior.STATE_COLLAPSED
    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    lateinit var rootNavController: NavController
    //endregion

    //region Overridden Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initData()

    }

    override fun onStop() {
        super.onStop()
        dataAccessLayer.dispose()
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
    //endregion
}
