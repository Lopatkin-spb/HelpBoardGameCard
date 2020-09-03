package space.lopatkin.spb.helpboardgamecard.ui;

import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.addCard.AddCardFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;



    private AddCardFragment addCardFragment;

    private Helpcard helpCard;

    private EditText editText;

    // private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //тулбар
        //Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        //knopka nazad тулбара
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);


////список
//        recyclerView = findViewById(R.id.list);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //разделитель
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//
//        Adapter adapter = new Adapter();
//        recyclerView.setAdapter(adapter);


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

//        //позволяет получить вьюмодел для нашей активити
//        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        mainViewModel.getHelpCardLiveData().observe(this, new Observer<List<HelpCard>>() {
//            @Override
//            public void onChanged(List<HelpCard> helpCards) {
//                adapter.setItems(helpCards);
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_helpcard,
                R.id.nav_addcard,
                R.id.nav_settings,
                R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    //создание верхнего меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addcard_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//    @SuppressLint("ResourceType")
//   public void startNewcardFragment() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        NewCardFragment newCardFragment = new NewCardFragment();
//        transaction.replace(R.layout.fragment_addcard , newCardFragment);
//        transaction.commit();
//    }



//    //обработка для событий
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
////            case android.R.id.home:
////                finish();
////                break;
//            case R.id.action_save:
//                if (editText.getText().length() > 0) {
//                    helpCard.text = editText.getText().toString();
//                    helpCard.favorites = false;
//                    helpCard.timestamp = System.currentTimeMillis();
//
//                    if (newCardFragment.getIntent().hasExtra(EXTRA_HELPCARD)) {
//                        App.getInstance().getHelpCardDao().update(helpCard);
//                    } else {
//                        App.getInstance().getHelpCardDao().insert(helpCard);
//                    }
//
//                    //finish();
//                }
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }



}
