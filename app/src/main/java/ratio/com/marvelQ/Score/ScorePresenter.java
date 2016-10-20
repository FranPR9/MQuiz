package ratio.com.marvelQ.Score;

import ratio.com.marvelQ.Score.event.ScoreEvent;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public interface ScorePresenter {

    void getScores(String token);
    void serverRespond(ScoreEvent event);

    void onCreate();
    void onDestroy();

}
