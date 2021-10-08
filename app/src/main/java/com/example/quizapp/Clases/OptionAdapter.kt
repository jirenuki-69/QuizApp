package com.example.quizapp.Clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.quizapp.R

class OptionAdapter(private val aContext: Context, private val array: ArrayList<Pareja>) :
    ArrayAdapter<Pareja>(aContext, R.layout.custom_list_view_item, array) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val text = getItem(position)?.first

        val view: View = convertView
            ?: LayoutInflater.from(aContext)
                .inflate(R.layout.custom_list_view_item, parent, false)

        val textView = view.findViewById<TextView>(R.id.question_option_text)

        textView!!.text = text

        return view
    }
}