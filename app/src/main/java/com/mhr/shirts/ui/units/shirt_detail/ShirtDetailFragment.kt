package com.mhr.shirts.ui.units.shirt_detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide

import com.mhr.shirts.R
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.disposables.CompositeDisposable

class ShirtDetailFragment : Fragment() {

    //region Fields
    companion object {
        fun newInstance() = ShirtDetailFragment()
    }

    private lateinit var viewModel: ShirtDetailViewModel
    private val disposables = CompositeDisposable()
    private var shirt: Shirt? = null

    //region Views
    lateinit var imageView: AppCompatImageView
    lateinit var nameTextView: AppCompatTextView
    lateinit var priceTextView: AppCompatTextView
    lateinit var sizeTextView: AppCompatTextView
    lateinit var colourTextView: AppCompatTextView
    lateinit var addToCartButton: AppCompatButton

    //endregion
    //endregion

    //region Functions
    private fun bind()
    {

    }

    private fun unBind()
    {
        disposables.clear()
    }

    private fun initViews(rootView: View)
    {
        imageView = rootView.findViewById(R.id.fragment_shirt_detail_image_view)
        nameTextView = rootView.findViewById(R.id.fragment_shirt_detail_name_text_view)
        priceTextView = rootView.findViewById(R.id.fragment_shirt_detail_price_text_view)
        sizeTextView = rootView.findViewById(R.id.fragment_shirt_detail_size_text_view)
        colourTextView = rootView.findViewById(R.id.fragment_shirt_detail_colour_text_view)
        addToCartButton = rootView.findViewById(R.id.fragment_shirt_detail_add_button)
        initActionListeners()
    }

    private fun initActionListeners()
    {
        addToCartButton.setOnClickListener {
            viewModel.onAddToBasketClicked(shirt!!)
        }
    }

    private fun adjustDataOnView(shirt: Shirt?)
    {
        if (shirt != null)
        {
            setImage(shirt.picture)
            nameTextView.text = shirt.name
            priceTextView.text = priceTextView.text.toString().replace(oldValue = "%v", newValue = shirt.price.toString().toUpperCase(), ignoreCase = false)
            sizeTextView.text = sizeTextView.text.toString().replace(oldValue = "%v", newValue = shirt.size.toString().toUpperCase(), ignoreCase = false)
            colourTextView.text = colourTextView.text.toString().replace(oldValue = "%v", newValue = shirt.colour.toString().toUpperCase(), ignoreCase = false)
        }
        else
        {
            goBack()
        }
    }

    private fun goBack()
    {
        activity?.onBackPressed()
    }

    private fun setImage(url: String?)
    {
        if (url != null)
        {
            Glide.with(imageView).load(url).into(imageView)
        }
        else
        {
            imageView.setImageResource(R.color.colorAccent)
        }
    }
    //endregion

    //region Overridden Functions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shirt_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shirt = arguments?.getParcelable("shirt")
        viewModel = ViewModelProviders.of(this).get(ShirtDetailViewModel::class.java)
        disposables.add(viewModel.unitIsReady())
        adjustDataOnView(shirt)
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
