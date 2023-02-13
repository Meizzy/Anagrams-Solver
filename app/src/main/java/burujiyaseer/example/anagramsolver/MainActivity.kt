package burujiyaseer.example.anagramsolver

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import burujiyaseer.example.anagramsolver.databinding.ActivityMainBinding
import burujiyaseer.example.anagramsolver.feature_anagram.ui.search.SearchActivity
import burujiyaseer.example.anagramsolver.feature_wordle.ui.NoticeDialogFragment
import burujiyaseer.example.anagramsolver.feature_wordle.ui.WordleFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NoticeDialogFragment.NoticeDialogListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate starts")
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        navView.itemIconTintList = null
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_main, R.id.nav_wordle, R.id.nav_word_of_the_day), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { item ->
            if (item.isChecked || item.itemId == R.id.mnu_word_of_the_day) {
                drawerLayout.closeDrawer(GravityCompat.START)
                return@setNavigationItemSelectedListener false
            }

            when (item.itemId) {
                R.id.mnu_search ->
                    launchSearchActivity()
                R.id.mnu_wordle -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.action_nav_main_to_nav_wordle)
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        toggle.syncState()
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun launchSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    override fun onDestroy() {
        binding.drawerLayout.removeDrawerListener(toggle)
        super.onDestroy()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {

    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        navController.navigate(R.id.action_nav_wordle_to_nav_main)
    }

}