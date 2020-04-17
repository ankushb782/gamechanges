package net.gahfy.mvvmposts.ui.post

import android.arch.lifecycle.MutableLiveData
import net.gahfy.mvvmposts.base.BaseViewModel
import net.gahfy.mvvmposts.model.PostDetail

class CommentDetailViewModel:BaseViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: PostDetail){
        postTitle.value = post.user.login
        postBody.value = post.body
    }

    fun getPostTitle():MutableLiveData<String>{
        return postTitle
    }

    fun getPostBody():MutableLiveData<String>{
        return postBody
    }
}