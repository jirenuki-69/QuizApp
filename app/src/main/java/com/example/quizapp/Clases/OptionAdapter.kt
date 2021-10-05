package com.example.quizapp.Clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.quizapp.R

class OptionAdapter(private val mcontext: Context, private val array: Array<String>) :
    ArrayAdapter<String>(mcontext, R.layout.custom_list_view_item, array) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val text = getItem(position)

        val view: View = convertView
            ?: LayoutInflater.from(mcontext)
                .inflate(R.layout.custom_list_view_item, parent, false)

        val textView = view.findViewById<TextView>(R.id.question_option_text)

        textView!!.text = text

        return view
    }
}