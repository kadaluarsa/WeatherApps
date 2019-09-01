package co.id.kadaluarsa.gojekassignment

import android.content.Context
import android.telephony.TelephonyManager


class Test {
    companion object {
        fun setMobileData(context: Context, enabled: Boolean) {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            val methodSet = Class.forName(tm!!.javaClass.name)
                .getDeclaredMethod("setDataEnabled", java.lang.Boolean.TYPE)
            methodSet.isAccessible = true
            methodSet.invoke(tm, enabled)
        }
    }
}