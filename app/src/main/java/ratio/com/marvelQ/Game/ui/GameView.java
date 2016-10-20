package ratio.com.marvelQ.Game.ui;

import java.util.List;

import ratio.com.marvelQ.entities.Option;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public interface GameView {

    void showProgress();
    void hideProgress();

    void showOptions(List<Option> options);
    void errorOnOptions();

    void presentImage(String image);

    void incrementScore();
    void removeLives();

    void showErrorButton();

    void showErrorSavingScore();
    void showSuccessSavingScore();

    void optionsVisible();
    void optionsGone();

    void showShareDialog();

}
