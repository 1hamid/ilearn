package ir.hamid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "w504")
data class W504(
    @PrimaryKey @ColumnInfo(name = "id") val wordId: Int,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "code") val code: String?,
    @ColumnInfo(name = "pronunciation") val pronunciation: String?,
    @ColumnInfo(name = "sample") val sample: String?,
    @ColumnInfo(name = "definition") val definition: String?,
    @ColumnInfo(name = "translate") val translate: String,
    @ColumnInfo(name = "review") val review: Int?
)
