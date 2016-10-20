package ratio.com.marvelQ.Game;

import ratio.com.marvelQ.Game.event.GameEvent;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public interface GamePresenter {

    void OnCreate();
    void OnDestroy();

    void getOptions(String private_apikey, String public_apikey);
    void OptionsRespond(GameEvent event);
    void saveScore(String id, int score);
    void removeAllExceptSelected(int position);
    void clearOptions();
}
