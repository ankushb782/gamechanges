package net.gahfy.mvvmposts.network

import io.reactivex.Observable
import net.gahfy.mvvmposts.model.Post
import net.gahfy.mvvmposts.model.PostDetail
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * The interface which provides methods to get result of webservices
 */
interface PostApi {
    /**
     * Get the list of the posts from the API
     */
    @GET("/repos/firebase/firebase-ios-sdk/issues")
    fun getPosts(): Observable<List<Post>>

    /**
     * Get the detail of the posts from the API
     */
    @POST("/repos/firebase/firebase-ios-sdk/issues/{ID}/comments")
    fun getPostDetail(@Path(value = "ID", encoded = true)  Id:Int): Observable<List<PostDetail>>
}