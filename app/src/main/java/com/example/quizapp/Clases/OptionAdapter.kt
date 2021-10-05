package com.example.quizapp.Clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.quizapp.R
import kotlinx.android.synthetic.main.custom_list_view_item.view.*

class OptionAdapter(private val mcontext: Context, private val array: Array<String>) : ArrayAdapter<String>(mcontext, 0, array) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mcontext).inflate(R.layout.custom_list_view_item, parent, false)

        layout.question_option_text.text = array[position]

        return layout
    }
}