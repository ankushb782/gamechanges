package net.gahfy.mvvmposts.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import net.gahfy.mvvmposts.model.database.AppDatabase
import net.gahfy.mvvmposts.ui.post.CommentDetailListViewModel
import net.gahfy.mvvmposts.ui.post.IssueListViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssueListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").build()
            @Suppress("UNCHECKED_CAST")
            return IssueListViewModel(db.postDao(),activity.applicationContext) as T
        }
        if (modelClass.isAssignableFrom(CommentDetailListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "postdetail").build()
            @Suppress("UNCHECKED_CAST")
            return CommentDetailListViewModel(db.postDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}