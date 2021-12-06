package com.example.quizapp.Clases

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.db.Entities.Profile

class ProfileAdapter(
    val profiles: MutableList<Profile>,
    val onSelect: (Int) -> Unit,
    val onDelete: (Int) -> Unit,
    val onEdit: (Int) -> Unit
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var btnName: Button
        private var btnEdit: Button
        private var btnDelete: Button

        private val greenColor = view.context.resources.getColor(R.color.green)
        private val whiteColor = view.context.resources.getColor(R.color.white)

        init {
            btnName = view.findViewById(R.id.profile_select_button)
            btnEdit = view.findViewById(R.id.profile_edit_button)
            btnDelete = view.findViewById(R.id.profile_delete_button)
        }

        fun bind(profile: Profile, position: Int) {
            btnName.text = profile.name
            btnName.setTextColor(
                when (profile.activeSession) {
                    true -> greenColor
                    false -> whiteColor
                }
            )

            btnName.setOnClickListener {
                onSelect(position)
            }

            btnEdit.setOnClickListener {
                onEdit(position)
            }

            btnDelete.setOnClickListener {
                onDelete(position)
            }
        }
    }

    fun getItem(position: Int): Profile = profiles[position]

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(profile: Profile) {
        profiles.add(profile)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        profiles.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProfileName(position: Int, newName: String) {
        profiles[position].name = newName
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSessions(newSessionPosition: Int, otherProfile: Profile?) {
        profiles[newSessionPosition].activeSession = true

        if (otherProfile != null) {
            val index = profiles.indexOf(otherProfile)
            if (index != -1) {
                profiles[index].activeSession = false
            }
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.profile_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(profiles[position], position)
    }

    override fun getItemCount(): Int = profiles.size
}