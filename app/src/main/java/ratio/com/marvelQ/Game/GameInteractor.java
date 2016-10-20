package ratio.com.marvelQ.Game;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public interface GameInteractor {

    void executeOptions(String letter, int offset, String ts,String apikey,String hash  );
    void executeSaveScore(String id, int score);

}
