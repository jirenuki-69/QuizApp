package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.db.Entities.Category
import com.example.quizapp.db.Entities.SettingsCategories

@Dao
interface SettingsCategoriesDao {
  @Query("SELECT * FROM categories WHERE id IN (SELECT category_id FROM settings_categories WHERE settings_id = (:settingsId))")
  fun getCategories(settingsId: Int) : MutableList<Category>

  @Query("DELETE FROM settings_categories WHERE settings_id = (:settingsId)")
  fun clearDataBySettingsId(settingsId: Int)

  @Insert
  fun insert(settingsCategories: SettingsCategories)

  @Insert
  fun insertAll(vararg settingsCategories: SettingsCategories)
}