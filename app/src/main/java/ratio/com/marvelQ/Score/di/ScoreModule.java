package ratio.com.marvelQ.Score.di;

import org.json.JSONArray;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ratio.com.marvelQ.Score.ScoreInteractor;
import ratio.com.marvelQ.Score.ScoreInteractorImpl;
import ratio.com.marvelQ.Score.ScorePresenter;
import ratio.com.marvelQ.Score.ScorePresenterImpl;
import ratio.com.marvelQ.Score.ScoreRepository;
import ratio.com.marvelQ.Score.ScoreRepositoryImpl;
import ratio.com.marvelQ.Score.ui.ScoreView;
import ratio.com.marvelQ.Score.ui.adapter.AdapterScores;
import ratio.com.marvelQ.lib.EventBus;

/**
 * Created by FranciscoPR on 18/10/16.
 */
@Module
public class ScoreModule {

    ScoreView view;

    public ScoreModule(ScoreView view) {
        this.view = view;
    }

    @Singleton
    @Provides
    ScorePresenter providesScorePresenter(EventBus eventBus, ScoreInteractor interactor){
        return new ScorePresenterImpl(eventBus, interactor, view);
    }

    @Singleton
    @Provides
    ScoreInteractor providesScoreInteractor(ScoreRepository repository){
        return new ScoreInteractorImpl(repository);
    }

    @Singleton
    @Provides
    ScoreRepository providesScoreRepository(EventBus eventBus){
        return  new ScoreRepositoryImpl(eventBus);
    }

    @Singleton
    @Provides
    AdapterScores providesAdapterScores(JSONArray scores){
        return  new AdapterScores(scores);
    }

    @Singleton
    @Provides
    JSONArray providesJSONArray(){
        return  new JSONArray();
    }



}
