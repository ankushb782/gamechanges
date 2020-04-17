package net.gahfy.mvvmposts.ui.post

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.hardikgoswami.oauthLibGithub.GithubOauth
import net.gahfy.mvvmposts.R


class LoginActivity: AppCompatActivity() {

    private var errorSnackbar: Snackbar? = null

    // you should either define client id and secret as constants or in string resources
    private val clientId = "150fe4a3187dba10a0b9"
    private val clientSecret = "54d5e4e81de55786944ffd305de23f008c711f36"
    private val redirectUri = "your://redirecturi"
    var sharedPreferences: SharedPreferences? = null
    companion object{
        val PREFERENCE = "github_prefs"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post_list)

        val scopeList: ArrayList<String>? = ArrayList()
        scopeList?.add("repo")
        scopeList?.add("gist")
        scopeList?.add("user")
        GithubOauth
                .Builder()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withContext(this@LoginActivity)
                .packageName("net.gahfy.mvvmposts")
                .nextActivity("net.gahfy.mvvmposts.ui.post.PostListActivity")
                .debug(true)
                .withScopeList(scopeList)
                .execute()


    }


}