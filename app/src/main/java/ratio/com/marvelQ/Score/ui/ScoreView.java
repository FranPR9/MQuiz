package ratio.com.marvelQ.Score.ui;

import org.json.JSONArray;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public interface ScoreView {

    void showProgress();
    void hideProgress();
    void showHighScores(JSONArray scores);
    void showHighScoresError();

    void startNewGame();


}
