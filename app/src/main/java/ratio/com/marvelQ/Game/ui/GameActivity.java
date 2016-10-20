package ratio.com.marvelQ.Game.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.AccessToken;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ratio.com.marvelQ.Game.GamePresenter;
import ratio.com.marvelQ.Game.di.GameComponent;
import ratio.com.marvelQ.Game.ui.adapter.AdapterOptions;
import ratio.com.marvelQ.Game.ui.adapter.OptionClick;
import ratio.com.marvelQ.entities.Option;
import ratio.com.marvelQ.R;
import ratio.com.marvelQ.lib.ImageLoader;
import ratio.com.marvelQ.marvelQApp;

public class GameActivity extends AppCompatActivity implements OptionClick, GameView, RequestListener {

    private static final String TAG = "GameView";

    //RequestManager requestManager;
    //ScoreRequest scoreRequest;
    //Option[] options;
    //Profile actualprofile;

    ImageView image;
    RecyclerView mRecyclerView;
    TextView scorev, livesv;
    @Bind(R.id.game_progress)
    ProgressBar gameProgress;
    @Bind(R.id.game_container)
    LinearLayout gameContainer;
    AdapterOptions mAdapter;
    @Bind(R.id.continue_error)
    Button continueError;
    @Bind(R.id.selected_option_name)
    TextView selectedOptionName;
    @Bind(R.id.selected_card)
    CardView selectedCard;

    private int score;
    private int errors;
    private String private_key = "";
    private String apikey = "";
    private String token;
    private ImageLoader imageLoader;
    private GamePresenter presenter;
    private int final_score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        apikey = getString(R.string.MARVEL_PUBLIC);
        private_key = getString(R.string.MARVEL_PRIVATE);

        errors = 3;
        score = 0;

        token = getIntent().getStringExtra("token");

        setInjection();

        setRecycler();

        image = (ImageView) findViewById(R.id.mainimg);
        scorev = (TextView) findViewById(R.id.actualscore);
        livesv = (TextView) findViewById(R.id.lives);
        scorev.setText("" + score);
        livesv.setText("" + errors);

        presenter.OnCreate();
        presenter.getOptions(private_key, apikey);

    }

    private void setInjection() {
        GameComponent component = ((marvelQApp) getApplication()).getGameComponent(this, this, this, this);
        presenter = component.getGamePresenter();
        mAdapter = component.getOptionsAdapter();
        imageLoader = component.getImageLoader();
    }

    private void setRecycler() {
        mRecyclerView = (RecyclerView) findViewById(R.id.optionsrcyclr);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setOnClickListener(this);
    }

/*
    @Override
    public void getOptions(Option[] options) {

        AdapterOptions mAdapter = new AdapterOptions(options, this);
        this.options = options;
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Picasso.with(this).load(options[FindAnsw()].img).into(image);

    }

    public int FindAnsw() {

        for (int i = 0; i < options.length; i++) {
            if (options[i].isAnswer()) {
                return i;
            }
        }

        return 0;

    }
    */




    public void GameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.gameOver));
        builder.setMessage("Score:" + score)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Log.d("gameover", "gameover button!!");
                        finish();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        if (errors == 0)
            super.onBackPressed();
        Log.d("logd", "backback!!");
    }

    boolean click = false;

    @Override
    public void onOptionClick(Option option, int position) {
        optionsGone();
        selectedOptionName.setText(option.getTitle());
        selectedCard.setVisibility(View.VISIBLE);
        if (!click) {
            click = true;
            mAdapter.setClick(true);
            presenter.removeAllExceptSelected(position);
            if (option.isAnswer()) {
                Correct();
            } else {
                Incorrect();
            }

            //gameContainer.setVisibility(View.GONE);
        }


    }

    private void Correct() {
        score++;
        incrementScore();
        presenter.getOptions(private_key, apikey);
        selectedCard.setBackgroundColor(Color.GREEN);
        //requestManager.fourRequests();
    }


    private void Incorrect() {
        errors--;
        removeLives();
        if (errors == 0) {
            Log.d("gameover", "GAME OVER");
            presenter.saveScore(token, score);
            final_score = score;
        } else {

            presenter.getOptions(private_key, apikey);
            //requestManager.fourRequests();
        }
        selectedCard.setBackgroundColor(Color.RED);
    }

    @Override
    public void showProgress() {
        gameProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        gameProgress.setVisibility(View.GONE);
    }

    @Override
    public void showOptions(List<Option> options) {
        gameContainer.setVisibility(View.VISIBLE);
        click = false;
        mAdapter.setOptions(options);
    }

    @Override
    public void errorOnOptions() {
        Toast.makeText(this, getString(R.string.optionserror), Toast.LENGTH_SHORT);
    }

    @Override
    public void presentImage(String image_url) {
        imageLoader.load(image, image_url);
    }

    @Override
    public void incrementScore() {
        scorev.setText("" + score);
    }

    @Override
    public void removeLives() {
        livesv.setText("" + errors);
    }

    @Override
    public void showErrorButton() {
        continueError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorSavingScore() {
        Toast.makeText(this, getString(R.string.errorSavingScore), Toast.LENGTH_SHORT);

    }

    @Override
    public void showSuccessSavingScore() {
        Toast.makeText(this, getString(R.string.succesSavingScore), Toast.LENGTH_SHORT);
    }

    @Override
    public void optionsVisible() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void optionsGone() {
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showShareDialog() {
        if(AccessToken.getCurrentAccessToken() != null){

            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=ratio.com.marvelQ"))
                    .setContentTitle(getString(R.string.app_name))
                    .setImageUrl(Uri.parse("https://lh3.googleusercontent.com/ke4hnZQ0ZNbHHpdw_njCyYGFvDcfPA_EyNFaGTcgNIgaFdlbzJ7EQngOU5FJispkAQ=w300-rw"))
                    .setContentDescription(getString(R.string.shareDialogText)+" "+final_score)
                    .build()
                    ;

            ShareDialog.show(this, content);
        }

        GameOverDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
    }

    @OnClick(R.id.continue_error)
    public void continueClick() {
        continueError.setVisibility(View.GONE);
        presenter.getOptions(private_key, apikey);
    }

    @Override
    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
        Log.d(TAG, "error loading image");
        //showErrorButton();
        presenter.getOptions(private_key, apikey);
        return false;
    }

    @Override
    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
        Log.d(TAG, "image Loaded");
        selectedCard.setBackgroundColor(Color.WHITE);
        selectedCard.setVisibility(View.GONE);
        optionsVisible();
        return false;
    }
}
