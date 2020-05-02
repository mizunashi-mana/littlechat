package com.example.littlechat.support.android.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.adapters.TextViewBindingAdapter

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // do nothing
        }
    })
}
