package net.gahfy.mvvmposts.ui.post

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.view.View
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import net.gahfy.mvvmposts.R
import net.gahfy.mvvmposts.base.BaseViewModel
import net.gahfy.mvvmposts.model.PostDao
import net.gahfy.mvvmposts.model.PostDetail
import net.gahfy.mvvmposts.network.PostApi
import javax.inject.Inject


class CommentDetailListViewModel(private val postDao: PostDao):BaseViewModel(){
    @Inject
    lateinit var postApi: PostApi
    val commentListAdapter: CommentDetailListAdapter = CommentDetailListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    private lateinit var subscription: Disposable

    init{
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    @SuppressLint("CheckResult")
    fun deleteAllPosts() {

        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                postDao.deletePostDetailAll()
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{})


    }
    private fun loadPosts(){
        deleteAllPosts()
        subscription = Observable.fromCallable { postDao.allDetail }
                .concatMap {

                    dbPostDetailList ->
                        if(dbPostDetailList.isEmpty())
                            postApi.getPostDetail(CommentsDetailActivity.id).concatMap {
                                            apiPostDetailList -> postDao.insertDetailAll(*apiPostDetailList.toTypedArray())
                                 Observable.just(apiPostDetailList)
                                       }
                        else
                            Observable.just(dbPostDetailList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                        { result -> onRetrievePostDetailListSuccess(result) },
                        { error-> onRetrievePostListError(error) }
                )

    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostDetailListSuccess(postList:List<PostDetail>){
        commentListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(error: Throwable) {
        println("error==="+error)
        errorMessage.value = R.string.post_error
    }
}