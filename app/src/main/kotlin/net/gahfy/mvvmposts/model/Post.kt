package net.gahfy.mvvmposts.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Class which provides a model for post
 * @constructor Sets all properties of the post
 * @property userId the unique identifier of the author of the post
 * @property number the unique identifier of the post
 * @property title the title of the post
 * @property body the content of the post
 */
@Entity
data class Post(
        val userId: Int,
        @field:PrimaryKey
        val number: Int,
        val title: String,
        val body: String

)