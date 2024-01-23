package space.lopatkin.spb.helpboardgamecard.presentation;


import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import org.greenrobot.eventbus.EventBus;
import space.lopatkin.spb.helpboardgamecard.BuildConfig;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.databinding.DrawerActivityMainBinding;
import space.lopatkin.spb.helpboardgamecard.databinding.DrawerNavHeaderMainBinding;

public class MainActivity extends AppCompatActivity {

    private DrawerActivityMainBinding binding;

    private DrawerNavHeaderMainBinding bindingHeader;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DrawerActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setToolbar();
        setNavigation();
        setApplicationVersion();
        onActionOpenDrawer();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        hideCustomKeyboard(); //Maybe move code to keyboard util
        hideDeviceKeyboard(binding.drawerLayout); //Maybe move code to keyboard util
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        hideCustomKeyboard(); //Maybe move code to keyboard util
        super.onBackPressed();
    }

    private void onActionOpenDrawer() {
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                hideCustomKeyboard(); //Maybe move code to keyboard util
                hideDeviceKeyboard(drawerView); //Maybe move code to keyboard util
            }
        });
    }

    private void setToolbar() {
//        тулбар должен быть инициализирован, чтоб на фрагментах можно было их установить
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void hideDeviceKeyboard(View view) {
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager inputManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void hideCustomKeyboard() {
        EventBus.getDefault().post(new KeyboardDoneEvent());
    }

    private void setApplicationVersion() {
        bindingHeader = DrawerNavHeaderMainBinding.bind(binding.navView.getHeaderView(0));
        String appVersion = getString(R.string.text_app_version, BuildConfig.VERSION_NAME);
        bindingHeader.textAppVersion.setText(appVersion);
    }

    private void setFloatingActionButton() {
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

    private void setNavigation() {
        //навигейшин архитекчер компонент
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_catalog,
                R.id.nav_addcard,
                R.id.nav_share,
                R.id.nav_settings)
                .setDrawerLayout(binding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}