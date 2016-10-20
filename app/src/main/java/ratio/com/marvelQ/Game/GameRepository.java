package ratio.com.marvelQ.Game;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public interface GameRepository {

    void getMarvelApiCharacter(String letter, int offset, String ts,String apikey,String hash  );
    void saveUserScore(String id, int score);

}
