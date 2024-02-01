package space.lopatkin.spb.helpboardgamecard.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import org.greenrobot.eventbus.EventBus
import space.lopatkin.spb.helpboardgamecard.BuildConfig
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication
import space.lopatkin.spb.helpboardgamecard.databinding.DrawerActivityMainBinding
import space.lopatkin.spb.helpboardgamecard.databinding.DrawerNavHeaderMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: DrawerActivityMainBinding? = null
    private var bindingHeader: DrawerNavHeaderMainBinding? = null
    private var mAppBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = DrawerActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        setToolbar()
        setNavigation()
        setApplicationVersion()
        onActionOpenDrawer()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideCustomKeyboard() //Maybe move code to keyboard util
        hideDeviceKeyboard(binding!!.drawerLayout) //Maybe move code to keyboard util
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        hideCustomKeyboard() //Maybe move code to keyboard util
        super.onBackPressed()
    }

    private fun onActionOpenDrawer() {
        if (binding != null) {
            binding!!.drawerLayout.addDrawerListener(object : SimpleDrawerListener() {
                override fun onDrawerOpened(drawerView: View) {
                    hideCustomKeyboard() //Maybe move code to keyboard util
                    hideDeviceKeyboard(drawerView) //Maybe move code to keyboard util
                }
            })
        }
    }

    private fun setToolbar() {
//        тулбар должен быть инициализирован, чтоб на фрагментах можно было их установить
        if (binding != null) {
            setSupportActionBar(binding!!.includeDrawerAppBarMain.toolbar)
        }
    }

    private fun hideDeviceKeyboard(view: View?) {
        if (view != null && view.windowToken != null) {
            val inputManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun hideCustomKeyboard() {
        EventBus.getDefault().post(KeyboardDoneEvent())
    }

    private fun setApplicationVersion() {
        if (binding != null) {
            bindingHeader = DrawerNavHeaderMainBinding.bind(binding!!.navView.getHeaderView(0))
            val appVersion: String = getString(R.string.text_app_version, BuildConfig.VERSION_NAME)
            if (bindingHeader != null) {
                bindingHeader!!.textAppVersion.text = appVersion
            }
        }
    }

    private fun setFloatingActionButton() {
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // NewCardFragment.start(MainActivity.this, null);
//
//
////было
//                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                //        .setAction("Action", null).show();
//            }
//        });
    }

    private fun setNavigation() {
        //навигейшин архитекчер компонент
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_catalog,
            R.id.nav_addcard,
            R.id.nav_share,
            R.id.nav_settings
        )
            .setDrawerLayout(binding!!.drawerLayout)
            .build()
        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        NavigationUI.setupWithNavController(binding!!.navView, navController)
    }

}
