package com.example.myfavoritemoviesmvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfavoritemoviesmvvm.model.movie.Movie
import com.example.myfavoritemoviesmvvm.model.movie.MovieDAO
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myfavoritemoviesmvvm.model.movie.MovieRepository
import com.example.myfavoritemoviesmvvm.model.movie.MovieViewModel
import com.example.myfavoritemoviesmvvm.model.genre.*


@Database(entities = [Genre::class, Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val genreDao: GenreDAO
    abstract val movieDao: MovieDAO

    companion object {
        lateinit var mContext: Context

        //         Singleton prevents multiple instances of database opening at the
//         same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            mContext = context
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                )
                    .addCallback(CALLBACK)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


//            @Volatile
//            private var instance: AppDatabase? = null
//            private val LOCK = Any()
//
//            operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//                instance ?: createDatabase(context).also {
//                    instance = it
//                }
//            }
//
//            private fun createDatabase(context: Context): AppDatabase {
//                return Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "contacts"
//                ).build()
//            }


        //
        var CALLBACK: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                // do something after database has been created

                val genreRepository =
                    GenreRepository(mContext.let { AppDatabase.getDatabase(it).genreDao })
                val genreViewModel = GenreViewModel(genreRepository)


                val comedyGenre = Genre()
                comedyGenre.genreName = "Comedy"

                val romanceGenre = Genre()
                romanceGenre.genreName = "Romance"

                val dramaGenre = Genre()
                dramaGenre.genreName = "Drama"


                genreViewModel.insert(comedyGenre)
                genreViewModel.insert(romanceGenre)
                genreViewModel.insert(dramaGenre)



                val movieRepository =
                    MovieRepository(mContext.let { AppDatabase.getDatabase(it).movieDao })
                val movieViewModel = MovieViewModel(movieRepository)

                val movie1 = Movie()
                movie1.title = "Bad Boys for Life"
                movie1.description =
                    "The Bad Boys Mike Lowrey and Marcus Burnett are back together for one last ride in the highly anticipated Bad Boys for Life."
                movie1.genreId = "1"

                val movie2 = Movie()
                movie2.title = "Parasite"
                movie2.description =
                    "All unemployed, Ki-taek and his family take peculiar interest in the wealthy and glamorous Parks, as they ingratiate themselves into their lives and get entangled in an unexpected incident."
                movie2.genreId = "1"


                val movie3 = Movie()
                movie3.title = "You"
                movie3.description =
                    "A dangerously charming, intensely obsessive young man goes to extreme measures to insert himself into the lives of those he is transfixed by."
                movie3.genreId = "2"

                val movie4 = Movie()
                movie4.title = "Little Women"
                movie4.description =
                    "Jo March reflects back and forth on her life, telling the beloved story of the March sisters - four young women each determined to live life on their own terms."
                movie4.genreId = "2"


                movieViewModel.insert(movie1)
                movieViewModel.insert(movie2)
                movieViewModel.insert(movie3)
                movieViewModel.insert(movie4)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                // do something every time database is open
            }
        }
    }
}