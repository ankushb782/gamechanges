package net.gahfy.mvvmposts.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PostDao {
    @get:Query("SELECT * FROM post")
    val all: List<Post>

    @get:Query("SELECT * FROM PostDetail")
    val allDetail: List<PostDetail>

    @Insert
    fun insertAll(vararg users: Post)

    @Insert
    fun insertDetailAll(vararg users: PostDetail)

    @Query("DELETE FROM post")
    fun deleteAll()

    @Query("DELETE FROM PostDetail")
    fun deletePostDetailAll()
}