package ratio.com.marvelQ.Score.di;

import javax.inject.Singleton;

import dagger.Component;
import ratio.com.marvelQ.Score.ScorePresenter;
import ratio.com.marvelQ.Score.ui.adapter.AdapterScores;
import ratio.com.marvelQ.lib.ImageLoader;
import ratio.com.marvelQ.lib.di.LibsModule;

/**
 * Created by FranciscoPR on 18/10/16.
 */
@Singleton
@Component(modules = {LibsModule.class,ScoreModule.class})
public interface ScoreComponent {

    ScorePresenter getScorePresenter();
    AdapterScores getAdapterScores();
    ImageLoader getImageLoader();

}
