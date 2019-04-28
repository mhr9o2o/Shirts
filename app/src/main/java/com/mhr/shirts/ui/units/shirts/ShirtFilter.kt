package com.mhr.shirts.ui.units.shirts

/**
 * Model to use for applying filters on shirts
 */
data class ShirtFilter(val size: String, val colour: String)
{
    companion object
    {
        const val FILTER_NONE_LITERAL_TEXT = "Any"
    }
}