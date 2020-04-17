package net.gahfy.mvvmposts.ui.post

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.gahfy.mvvmposts.R
import net.gahfy.mvvmposts.databinding.ItemPostBinding
import net.gahfy.mvvmposts.model.Post

class IssueListAdapter(var mContext: Context): RecyclerView.Adapter<IssueListAdapter.ViewHolder>() {
    private lateinit var postList:List<Post>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueListAdapter.ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueListAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext,CommentsDetailActivity::class.java)
            intent.putExtra("Id",postList[position].number)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if(::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(postList:List<Post>){
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = IssueViewModel()

        fun bind(post:Post){
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}