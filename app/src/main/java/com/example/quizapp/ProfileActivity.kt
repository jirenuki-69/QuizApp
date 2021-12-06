package com.example.quizapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.quizapp.db.Entities.Settings
import com.example.quizapp.db.Entities.SettingsCategories

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
            onDelete = { index -> deleteProfile(index) },
            onEdit = { index -> openDialog(index, true) }
        )

        rv.adapter = adapter

        backButton.setOnClickListener {
            finish()
        }

        addProfileButton.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog(position: Int = 0, edit: Boolean = false) {
        val dialog = Dialog(edit, position)
        dialog.show(supportFragmentManager, "Profile Dialog")
    }

    private fun setNewActiveProfile(position: Int) {
        val selectedProfile = adapter.getItem(position)

        if (selectedProfile.activeSession) {
            return
        }

        val otherProfile = profileDao.getActiveProfile()

        selectedProfile.activeSession = true
        profileDao.update(selectedProfile)

        adapter.updateSessions(position, otherProfile)

        if (otherProfile != null) {
            otherProfile.activeSession = false
            profileDao.update(otherProfile)
        }
    }

    private fun deleteProfile(position: Int) {
        val builder = AlertDialog.Builder(this as Context)
        builder.setCancelable(false)
        builder.setTitle(resources.getString(R.string.delete_text))
        builder.setMessage(resources.getString(R.string.delete_profile_content))
        builder.setPositiveButton(resources.getString(R.string.yes_text)) { _, _ ->
            deleteAllProfileRelatedData(position)
        }
        builder.setNegativeButton(resources.getString(R.string.no_text)) { dialog, _ ->
            dialog.cancel()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun deleteAllProfileRelatedData(position: Int) {
        val deletedProfile = adapter.getItem(position)
        profileDao.delete(deletedProfile)
        adapter.removeItem(position)

        db.SettingsDao().deleteProfileSettings(deletedProfile.id)
        db.GameDao().deleteProfileGames(deletedProfile.id)
        db.ScoreDao().deleteProfileScores(deletedProfile.id)

        Toast.makeText(
            this as Context,
            resources.getString(R.string.profile_deleted),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun createProfileSettings(newProfile: Profile) {
        val settings = Settings(
            profileId = newProfile.id,
            numberOfQuestions = 8,
            difficulty = "Medio",
            hintsEnabled = true
        )

        val settingsId = db.SettingsDao().insert(settings)

        val firstCategory = SettingsCategories(
            categoryId = 21,
            settingsId = settingsId.toInt()
        )
        val secondCategory = SettingsCategories(
            categoryId = 22,
            settingsId = settingsId.toInt()
        )

        db.SettingsCategoriesDao().insertAll(firstCategory, secondCategory)
    }

    override fun applyText(name: String, edit: Boolean, position: Int) {
        if (name.isNotEmpty()) {
            when (edit) {
                true -> {
                    val profile = adapter.getItem(position)
                    profile.name = name

                    profileDao.update(profile)
                    adapter.updateProfileName(position, name)
                }
                false -> {
                    val profile = Profile(name = name, activeSession = false)
                    val profileId = profileDao.insert(profile)
                    profile.id = profileId.toInt()
                    adapter.addItem(profile)

                    createProfileSettings(profile)

                    Toast.makeText(
                        this as Context,
                        resources.getString(R.string.profile_created),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                this as Context,
                "No Name",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}