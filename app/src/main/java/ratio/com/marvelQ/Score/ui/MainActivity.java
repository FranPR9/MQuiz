package ratio.com.marvelQ.Score.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import ratio.com.marvelQ.Score.ScorePresenter;
import ratio.com.marvelQ.Score.di.ScoreComponent;
import ratio.com.marvelQ.Score.ui.adapter.AdapterScores;
import ratio.com.marvelQ.Game.ui.GameActivity;
import ratio.com.marvelQ.R;
import ratio.com.marvelQ.lib.ImageLoader;
import ratio.com.marvelQ.marvelQApp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ScoreView {

    RecyclerView scores; //enfocado más al material design, librerias anexas en el gradle. Sumamente parecido a listview
    //ScoreRequest scoreRequest; //clase que maneja las request al servidor que insertan scores o reciben scores
    @Bind(R.id.scores_progress)
    ProgressBar scoresProgress;
    private String profile_image;
    private ImageLoader imageLoader;
    private AdapterScores mAdapter;
    private ScorePresenter presenter;
    private String token;
    private boolean game_started= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Marvel Quiz");
        setSupportActionBar(toolbar);
        //FacebookSdk.sdkInitialize(getApplicationContext());

        setInjection();

        token = getIntent().getStringExtra("token");

        setDrawernNav(toolbar);

        setRecycler();

        //scoreRequest = new ScoreRequest(actualprofile.getId(), this, this);
        //scoreRequest.getScores();


        Button begin = (Button) findViewById(R.id.begin);
        begin.setOnClickListener(this);

        presenter.onCreate();
        presenter.getScores(token);
    }

    private void setInjection() {
        ScoreComponent component = ((marvelQApp)getApplication()).getScoreComponent(this,this);
        presenter = component.getScorePresenter();
        mAdapter = component.getAdapterScores();
        imageLoader = component.getImageLoader();
    }

    private void setRecycler() {
        scores = (RecyclerView) findViewById(R.id.scores);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        scores.setLayoutManager(mLayoutManager);
        scores.setAdapter(mAdapter);
    }

    private void setDrawernNav(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if (getIntent().getStringExtra("name") != null) {
            TextView username = (TextView) findViewById(R.id.txtusername);
            username.setText(getIntent().getStringExtra("name")); //agregas el nombre al drawermenu
        }
        if (getIntent().getStringExtra("last") != null) {
            TextView email = (TextView) findViewById(R.id.email);
            email.setText(getIntent().getStringExtra("last")); //agregas el apellido al drawer menu

        }
        if (getIntent().getStringExtra("img") != null) {
            Log.d("img1", getIntent().getStringExtra("img"));
            profile_image = getIntent().getStringExtra("img");
            ImageView profile = (ImageView) findViewById(R.id.imageView1);
            imageLoader.load(profile,profile_image);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            // Handle the camera action
            LoginManager.getInstance().logOut();
            finish();
        } /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        StartGameDialog();

    }


    public void StartGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.begin));
        builder.setMessage(getString(R.string.instructs))
                .setPositiveButton(getString(R.string.begin), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Log.d("startgame", "startgame button!!");
                        Intent i = new Intent(MainActivity.this, GameActivity.class);
                        i.putExtra("token",token);
                        game_started = true;
                        startActivity(i);


                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Log.d("startgame", "cancel button!!");
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(game_started) {
            presenter.getScores(token);
            game_started = false;
        }

    }


    @Override
    public void showProgress() {
        scoresProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        scoresProgress.setVisibility(View.GONE);
    }

    @Override
    public void showHighScores(JSONArray ja_scores) {
        mAdapter.setScores(ja_scores);
    }

    @Override
    public void showHighScoresError() {
        Toast.makeText(this,getString(R.string.scores_error),Toast.LENGTH_SHORT);
    }

    @Override
    public void startNewGame() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
