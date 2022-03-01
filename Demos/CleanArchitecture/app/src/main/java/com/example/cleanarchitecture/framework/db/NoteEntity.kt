package com.example.cleanarchitecture.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val content: String,
    @ColumnInfo(name = "creation_date") val creationTime: Long,
    @ColumnInfo(name = "update_date") val updateTime: Long
) {
    fun toNote() = Note(id, title, content, creationTime, updateTime)

    companion object {
        fun fromNote(note: Note) = with(note) {
            NoteEntity(id, title, content, creationTime, updateTime)
        }
    }
}
