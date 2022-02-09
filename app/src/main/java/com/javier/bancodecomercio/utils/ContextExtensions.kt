package com.javier.bancodecomercio.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

inline fun <reified T: Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Activity> Activity.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

inline fun <reified T: Activity> Activity.intentFor(body: Intent.() -> Unit) =
    Intent(this, T::class.java).apply(body)

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}