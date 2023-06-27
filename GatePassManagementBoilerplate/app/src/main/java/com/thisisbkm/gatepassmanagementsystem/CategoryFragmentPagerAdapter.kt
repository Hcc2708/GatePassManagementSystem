
package com.thisisbkm.gatepassmanagementsystem

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.thisisbkm.gatepassmanagementsystem.fragments.*



/**
 * Provides the appropriate [Fragment] for a view pager.
 */
class CategoryFragmentPagerAdapter(
    /** Context of the app  */
    private val mContext: Context, fm: FragmentManager?
) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Constants.CHECKIN -> Checkin()
            Constants.SCANNER -> BarCode()
            Constants.CHECKOUT -> Checkout()
            Constants.LOGS -> Logs()
            else -> {Checkin()}
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        val titleResId: Int = when (position) {
            Constants.CHECKIN -> R.string.check_in
            Constants.SCANNER -> R.string.scanner
            Constants.CHECKOUT -> R.string.check_out
            Constants.LOGS -> R.string.logs
            else -> {R.string.check_in}
        }
        return mContext.getString(titleResId)
    }
}