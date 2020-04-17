package net.gahfy.mvvmposts.injection.component

import dagger.Component
import net.gahfy.mvvmposts.injection.module.NetworkModule
import net.gahfy.mvvmposts.ui.post.CommentDetailListViewModel
import net.gahfy.mvvmposts.ui.post.CommentDetailViewModel
import net.gahfy.mvvmposts.ui.post.IssueListViewModel
import net.gahfy.mvvmposts.ui.post.IssueViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param issueListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(issueListViewModel: IssueListViewModel)
    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param issueViewModel PostViewModel in which to inject the dependencies
     */
    fun inject(issueViewModel: IssueViewModel)
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param commentListViewModel PostDetailListViewModel in which to inject the dependencies
     */
    fun inject(commentListViewModel: CommentDetailListViewModel)
    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param PostDetailViewModel PostDetailViewModel in which to inject the dependencies
     */
    fun inject(commentViewModel: CommentDetailViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}