package ratio.com.marvelQ.Score;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public class ScoreInteractorImpl implements ScoreInteractor {

    ScoreRepository repository;

    public ScoreInteractorImpl(ScoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeScores(String token) {
        repository.getScores(token);
    }
}
