package com.example.anki.navigation

import android.os.Bundle
import androidx.navigation.NavArgs
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class SeeCardFragmentArgs(
    public val cardId: Int
) : NavArgs {
    public fun toBundle(): Bundle {
        val result = Bundle()
        result.putInt("cardId", this.cardId)
        return result
    }

    public companion object {
        @JvmStatic
        public fun fromBundle(bundle: Bundle): SeeCardFragmentArgs {
            bundle.setClassLoader(SeeCardFragmentArgs::class.java.classLoader)
            val __cardId: Int
            if (bundle.containsKey("cardId")) {
                __cardId = bundle.getInt("cardId")
            } else {
                __cardId = 0
            }
            return SeeCardFragmentArgs(__cardId)
        }
    }
}
