package com.example.myfavoritemoviesmvvm.model.genre

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres_table")
class Genre: BaseObservable() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "genre_name")
    @Bindable
    var genreName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.genreName)
        }

    @Override
    override fun toString(): String {
        return genreName.toString()
    }
}