package ratio.com.marvelQ.Game.di;

import javax.inject.Singleton;

import dagger.Component;

import ratio.com.marvelQ.Game.GamePresenter;
import ratio.com.marvelQ.Game.ui.adapter.AdapterOptions;
import ratio.com.marvelQ.lib.ImageLoader;
import ratio.com.marvelQ.lib.di.LibsModule;

/**
 * Created by FranciscoPR on 18/10/16.
 */
@Singleton
@Component(modules = {LibsModule.class,GameModule.class})
public interface GameComponent {

    GamePresenter getGamePresenter();
    ImageLoader getImageLoader();
    AdapterOptions getOptionsAdapter();
}
