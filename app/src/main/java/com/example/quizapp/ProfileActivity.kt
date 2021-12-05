package com.example.quizapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Clases.Dialog
import com.example.quizapp.Clases.ProfileAdapter
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Daos.ProfileDao
import com.example.quizapp.db.Entities.Profile

class ProfileActivity : AppCompatActivity(), Dialog.DialogListener {
    private lateinit var backButton: Button
    private lateinit var addProfileButton: Button
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProfileAdapter
    private lateinit var db: AppDatabase
    private lateinit var profiles: MutableList<Profile>
    private lateinit var profileDao: ProfileDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = AppDatabase.getInstance(this as Context)
        profileDao = db.ProfileDao()

        profiles = profileDao.getAllProfiles()

        backButton = findViewById(R.id.profile_backHome_button)
        addProfileButton = findViewById(R.id.profile_add_button)
        rv = findViewById(R.id.profile_items)
        rv.layoutManager = LinearLayoutManager(this)

        adapter = ProfileAdapter(
            profiles,
            onSelect = { index -> setNewActiveProfile(index) },
            onDelete = { index -> openDialog() },
            onEdit = { index -> deleteProfile(index) }
        )

        rv.adapter = adapter

        backButton.setOnClickListener {
            finish()
        }

        addProfileButton.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val dialog = Dialog()
        dialog.show(supportFragmentManager, "Profile Dialog")
    }

    private fun setNewActiveProfile(position: Int) {
        val profile = profileDao.getProfile(rv.adapter?.getItemId(position)!!.toInt())
        val otherProfile = profileDao.getActiveProfile()
        profile.activeSession = true

        profileDao.update(profile)

        if (otherProfile != null) {
            otherProfile.activeSession = false
            profileDao.update(otherProfile)
        }

        adapter.updateSessions(position, otherProfile)
    }

    private fun deleteProfile(position: Int) {
        val builder = AlertDialog.Builder(this as Context)
        builder.setCancelable(false)
        builder.setTitle(resources.getString(R.string.delete_text))
        builder.setMessage(resources.getString(R.string.delete_profile_content))
        builder.setPositiveButton(resources.getString(R.string.yes_text)) { _, _ ->
            db.ProfileDao().deleteFromId(rv.adapter?.getItemId(position)!!.toInt())
            adapter.removeItem(position)

            Toast.makeText(
                this as Context,
                resources.getString(R.string.profile_deleted),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton(resources.getString(R.string.no_text)) { dialog, _ ->
            // Do Nothing
            dialog.cancel()
        }

        val alert = builder.create()
        alert.show()
    }

    override fun applyText(name: String) {
        if (name.isNotEmpty()) {
            val profile = Profile(name = name, activeSession = false)
            profileDao.insert(profile)
            adapter.addItem(profile)

            Toast.makeText(
                this as Context,
                resources.getString(R.string.profile_created),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this as Context,
                "No Name",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}