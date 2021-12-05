package com.example.quizapp.Clases

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.quizapp.R
import kotlin.ClassCastException

class Dialog : AppCompatDialogFragment() {
    private lateinit var editTextName: EditText
    private lateinit var listener: DialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity as Context)
        val view = layoutInflater.inflate(R.layout.layout_dialog, null)

        builder.setView(view)
            .setTitle(resources.getString(R.string.new_profile))
            .setNegativeButton(resources.getString(R.string.cancel_text)) { _, _ ->

            }
            .setPositiveButton(resources.getString(R.string.create)) { _, _ ->
                val name = editTextName.text.toString()
                listener.applyText(name)
            }

        editTextName = view.findViewById(R.id.etName)

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "Must Implement DialogListener")
        }
    }

    interface DialogListener {
        fun applyText(name: String)
    }
}