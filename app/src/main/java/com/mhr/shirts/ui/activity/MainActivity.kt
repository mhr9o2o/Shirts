package com.mhr.shirts.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mhr.shirts.R

class MainActivity : AppCompatActivity() {
    //region Fields

    //endregion

    //region Overridden Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheetView: View = findViewById(R.id.activity_main_bottom_sheet_fragment)
        val rootView: View = findViewById(R.id.activity_main_root_fragment)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                rootView.alpha = 1f - slideOffset
            }
        })
    }
    //endregion
}
