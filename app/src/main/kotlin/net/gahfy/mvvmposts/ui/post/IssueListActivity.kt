package net.gahfy.mvvmposts.ui.post

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import net.gahfy.mvvmposts.R
import net.gahfy.mvvmposts.databinding.ActivityPostListBinding
import net.gahfy.mvvmposts.injection.ViewModelFactory


class IssueListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPostListBinding
    private lateinit var viewModel: IssueListViewModel
    private var errorSnackbar: Snackbar? = null

    // you should either define client id and secret as constants or in string resources
    private val clientId = "e538c8bb8b541127b54d"
    private val clientSecret = "bb62db48e3095ba85e070c3d5bb6d2244995ba91"
    private val redirectUri = "futurestudio://callback"
    var sharedPreferences: SharedPreferences? = null
    companion object{
        val PREFERENCE = "github_prefs"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_list)

      /*  sharedPreferences = getSharedPreferences(PREFERENCE, 0)
          var  oauthToken: String? = sharedPreferences?.getString("oauth_token", null)
        Log.d("FragmentActivity.TAG", "oauth token for github loged in user is :" + oauthToken)*/

        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(IssueListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer {
            errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }
}