package com.bangkit.resep.view

import android.content.Intent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bangkit.resep.R
import com.bangkit.resep.auth.SigninActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var bottomMenuView: BottomNavigationView
    private lateinit var auth: FirebaseAuth
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            startActivity(Intent(this@MainActivity, SigninActivity::class.java))
            finish()
            return
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomMenuView = findViewById(R.id.navigation_bottom)

        bottomMenuView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_chat -> {
                    replaceFragment(ChatFragment())
                    true
                }
                R.id.bottom_rizz -> {
                    replaceFragment(RizzFragment())
                    true
                }
                R.id.bottom_article -> {
                    replaceFragment(ArticleFragment())
                    true
                }
                R.id.bottom_profil -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(ChatFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        lifecycleScope.launch {
            try {
                auth.signOut()
                val credentialManager = CredentialManager.create(this@MainActivity)
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
                startActivity(Intent(this@MainActivity, SigninActivity::class.java))
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
