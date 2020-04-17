package net.gahfy.mvvmposts.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.gahfy.mvvmposts.model.Post
import net.gahfy.mvvmposts.model.PostDao
import net.gahfy.mvvmposts.model.PostDetail

@Database(entities = [Post::class,PostDetail::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}