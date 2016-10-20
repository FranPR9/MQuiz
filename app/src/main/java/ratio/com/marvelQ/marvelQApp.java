package ratio.com.marvelQ;

import android.app.Application;

import com.bumptech.glide.request.RequestListener;

import ratio.com.marvelQ.Game.di.DaggerGameComponent;
import ratio.com.marvelQ.Game.di.GameComponent;
import ratio.com.marvelQ.Game.di.GameModule;
import ratio.com.marvelQ.Game.ui.GameActivity;
import ratio.com.marvelQ.Game.ui.GameView;
import ratio.com.marvelQ.Game.ui.adapter.OptionClick;
import ratio.com.marvelQ.Login.di.DaggerLoginComponent;
import ratio.com.marvelQ.Login.di.LoginComponent;
import ratio.com.marvelQ.Login.di.LoginModule;
import ratio.com.marvelQ.Login.ui.FBLoginActivity;
import ratio.com.marvelQ.Login.ui.LoginView;
import ratio.com.marvelQ.Score.di.DaggerScoreComponent;
import ratio.com.marvelQ.Score.di.ScoreComponent;
import ratio.com.marvelQ.Score.di.ScoreModule;
import ratio.com.marvelQ.Score.ui.MainActivity;
import ratio.com.marvelQ.Score.ui.ScoreView;
import ratio.com.marvelQ.lib.di.LibsModule;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public class marvelQApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
    }

    private void initFacebook() {

    }


    public LoginComponent getLoginComponent(FBLoginActivity activity, LoginView v)
    {

        return DaggerLoginComponent.builder()
                .libsModule(new LibsModule(activity,null))
                .loginModule(new LoginModule(v))
                .build();

    }
    public ScoreComponent getScoreComponent(MainActivity activity, ScoreView scoreView)
    {
        return DaggerScoreComponent.builder()
                .libsModule(new LibsModule(activity,null))
                .scoreModule(new ScoreModule(scoreView))
                .build();
    }

    public GameComponent getGameComponent(GameActivity activity, GameView view, OptionClick optionClick, RequestListener requestListener){
        return DaggerGameComponent.builder()
                .libsModule(new LibsModule(activity,requestListener))
                .gameModule(new GameModule(view,optionClick))
                .build();
    }
}
