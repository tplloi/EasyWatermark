package com.mckimquyen.watermark.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mckimquyen.watermark.data.model.entity.Template
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {

    @Query("SELECT * FROM template ORDER BY creation_date DESC")
    fun getAllTemplate(): Flow<List<Template>>

    @Insert
    suspend fun insertTemplate(template: Template)

    @Delete
    suspend fun deleteTemplate(template: Template)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTemplate(template: Template)
}