package net.gahfy.mvvmposts.ui.post

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.view.View
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import net.gahfy.mvvmposts.R
import net.gahfy.mvvmposts.base.BaseViewModel
import net.gahfy.mvvmposts.model.Post
import net.gahfy.mvvmposts.model.PostDao
import net.gahfy.mvvmposts.network.PostApi
import javax.inject.Inject


class IssueListViewModel(private val postDao: PostDao, applicationContext: Context):BaseViewModel(){
    @Inject
    lateinit var postApi: PostApi
    val issueListAdapter: IssueListAdapter = IssueListAdapter(applicationContext)

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
                postDao.deleteAll()
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{})


    }
    private fun loadPosts(){
       // deleteAllPosts()
        subscription = Observable.fromCallable { postDao.all }
                .concatMap {

                    dbPostList ->
                        if(dbPostList.isEmpty())
                            postApi.getPosts().concatMap {
                                            apiPostList -> postDao.insertAll(*apiPostList.toTypedArray())
                                 Observable.just(apiPostList)
                                       }
                        else
                            Observable.just(dbPostList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                        { result -> onRetrievePostListSuccess(result) },
                        { error->onRetrievePostListError(error) }
                )
    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList:List<Post>){
        issueListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(error: Throwable) {
        println("error==="+error)
        errorMessage.value = R.string.post_error
    }


}