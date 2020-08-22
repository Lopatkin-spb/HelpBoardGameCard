package space.lopatkin.spb.helpboardgamecard.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import space.lopatkin.spb.helpboardgamecard.App;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.HelpCard;

public class HelpCardDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_HELPCARD = "HelpCardDetailsActivity.EXTRA_HELPCARD";

    private HelpCard helpCard;

    private EditText editText;

    //метод вызова етого активити извне
    // (такой способ упрощает код -
    // чем прописывать каждый раз при вызове -
    // проще написать один раз в самом активити)
    public static void start(Activity caller, HelpCard helpCard) {

        Intent intent = new Intent(caller, HelpCardDetailsActivity.class);

        if (helpCard != null) {
            intent.putExtra(EXTRA_HELPCARD, helpCard);
        }

        caller.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_helpcard_details);

        //
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //knopka nazad
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        setTitle(getString(R.string.helpcard_details_title));

        editText = findViewById(R.id.text);

        if (getIntent().hasExtra(EXTRA_HELPCARD)) {

            helpCard = getIntent().getParcelableExtra(EXTRA_HELPCARD);
            editText.setText(helpCard.text);

        } else {
            helpCard = new HelpCard();
        }

    }


    //создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //извлечение меню
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //обработка для событий
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if (editText.getText().length() > 0) {
                    helpCard.text = editText.getText().toString();
                    helpCard.favorites = false;
                    helpCard.timestamp = System.currentTimeMillis();

                    if (getIntent().hasExtra(EXTRA_HELPCARD)) {
                        App.getInstance().getHelpCardDao().update(helpCard);
                    } else {
                        App.getInstance().getHelpCardDao().insert(helpCard);
                    }

                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }





}
