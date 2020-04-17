package net.gahfy.mvvmposts.ui.post

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.gahfy.mvvmposts.R
import net.gahfy.mvvmposts.databinding.ItemPostDetailBinding
import net.gahfy.mvvmposts.model.PostDetail

class CommentDetailListAdapter: RecyclerView.Adapter<CommentDetailListAdapter.ViewHolder>() {
    private lateinit var postList:List<PostDetail>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentDetailListAdapter.ViewHolder {
        val binding: ItemPostDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post_detail, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentDetailListAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return if(::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(postList:List<PostDetail>){
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostDetailBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = CommentDetailViewModel()

        fun bind(post: PostDetail){
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}