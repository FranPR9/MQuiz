package ratio.com.marvelQ.Game;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public class GameInteractorImpl implements GameInteractor {
    GameRepository repository;

    public GameInteractorImpl(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeOptions(String letter, int offset, String ts,String apikey,String hash  ) {
        repository.getMarvelApiCharacter(letter, offset, ts, apikey, hash);
    }

    @Override
    public void executeSaveScore(String id, int score) {
        repository.saveUserScore(id, score);
    }
}
