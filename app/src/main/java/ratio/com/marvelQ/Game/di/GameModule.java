package ratio.com.marvelQ.Game.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ratio.com.marvelQ.Game.GameInteractor;
import ratio.com.marvelQ.Game.GameInteractorImpl;
import ratio.com.marvelQ.Game.GamePresenter;
import ratio.com.marvelQ.Game.GamePresenterImpl;
import ratio.com.marvelQ.Game.GameRepository;
import ratio.com.marvelQ.Game.GameRepositoryImpl;
import ratio.com.marvelQ.Game.ui.GameView;
import ratio.com.marvelQ.Game.ui.adapter.AdapterOptions;
import ratio.com.marvelQ.Game.ui.adapter.OptionClick;
import ratio.com.marvelQ.entities.Option;
import ratio.com.marvelQ.lib.EventBus;

/**
 * Created by FranciscoPR on 18/10/16.
 */
@Module
public class GameModule {

    GameView view;
    OptionClick optionClick;

    public GameModule(GameView view,OptionClick optionClick) {
        this.view = view;
        this.optionClick = optionClick;
    }

    @Singleton
    @Provides
    GamePresenter providesGamePresenter(EventBus eventBus, GameInteractor interactor,List<Option> optionList){
        return  new GamePresenterImpl(eventBus,view,interactor,optionList);
    }

    @Singleton
    @Provides
    GameInteractor providesGameInteractor(GameRepository repository){
        return  new GameInteractorImpl(repository);
    }

    @Singleton
    @Provides
    GameRepository providesGameRepository(EventBus eventBus){
        return  new GameRepositoryImpl(eventBus);
    }

    @Singleton
    @Provides
    List<Option> providesOptionList(){
        return new ArrayList<Option>();
    }

    @Singleton
    @Provides
    AdapterOptions providesAdapterOptions(List<Option> optionList){
        return  new AdapterOptions(optionList,optionClick);
    }



}
