package ratio.com.marvelQ.Score;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import ratio.com.marvelQ.Score.event.ScoreEvent;
import ratio.com.marvelQ.Score.ui.ScoreView;
import ratio.com.marvelQ.lib.EventBus;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public class ScorePresenterImpl implements ScorePresenter {

    private static final String TAG = "ScorePresenter";
    private EventBus  eventBus;
    private ScoreInteractor interactor;
    private ScoreView view;

    public ScorePresenterImpl(EventBus eventBus, ScoreInteractor interactor, ScoreView view) {
        this.eventBus = eventBus;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void getScores(String token) {
        view.showProgress();
        interactor.executeScores(token);
    }

    @Override
    @Subscribe
    public void serverRespond(ScoreEvent scoreEvent) {
        Log.d(TAG,scoreEvent.isSuccess()+""+scoreEvent.getType());
        if(scoreEvent.isSuccess()){
            Log.d(TAG,""+scoreEvent.getScores().length());
            switch (scoreEvent.getType()){
                case ScoreEvent.SCORES:
                    view.hideProgress();
                    view.showHighScores(scoreEvent.getScores());
                    break;
            }
        }
        else{
            Log.d(TAG,scoreEvent.getError()+"");
            switch (scoreEvent.getType()) {
                case ScoreEvent.SCORES:
                    view.hideProgress();
                    view.showHighScoresError();
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }
}
