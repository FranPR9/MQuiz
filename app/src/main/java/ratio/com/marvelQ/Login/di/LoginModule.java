package ratio.com.marvelQ.Login.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ratio.com.marvelQ.Login.LoginInteractor;
import ratio.com.marvelQ.Login.LoginInteractorImpl;
import ratio.com.marvelQ.Login.LoginPresenter;
import ratio.com.marvelQ.Login.LoginPresenterImp;
import ratio.com.marvelQ.Login.LoginRepository;
import ratio.com.marvelQ.Login.LoginRepositoryImpl;
import ratio.com.marvelQ.Login.ui.LoginView;
import ratio.com.marvelQ.lib.EventBus;

/**
 * Created by FranciscoPR on 17/10/16.
 */
@Module
public class LoginModule {
    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Singleton
    @Provides
    LoginPresenter provideLoginPresenter( EventBus eventBus, LoginInteractor interactor){
        return new LoginPresenterImp(view,eventBus,interactor);
    }
    @Singleton
    @Provides
    LoginInteractor provideLoginInteractor(LoginRepository repository){
        return new LoginInteractorImpl(repository);
    }

    @Singleton
    @Provides
    LoginRepository providesLoginRepository(EventBus eventBus)
    {
        return  new LoginRepositoryImpl(eventBus);
    }


}
