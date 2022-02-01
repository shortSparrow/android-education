package com.example.myfavoritemoviesmvvm.model.movie

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.myfavoritemoviesmvvm.model.genre.Genre

@Entity(
    tableName = "movies_table",
    foreignKeys = [ForeignKey(
        entity = Genre::class,
        parentColumns = ["id"],
        childColumns = ["genre_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Movie : BaseObservable() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "genre_id")
    var genreId = ""

    @Bindable
    var title = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @Bindable
    var description = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.description)
        }
}