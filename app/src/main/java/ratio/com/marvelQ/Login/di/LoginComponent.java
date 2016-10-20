package ratio.com.marvelQ.Login.di;

import javax.inject.Singleton;

import dagger.Component;
import ratio.com.marvelQ.Login.LoginPresenter;
import ratio.com.marvelQ.lib.di.LibsModule;

/**
 * Created by FranciscoPR on 17/10/16.
 */
@Singleton
@Component(modules = {LibsModule.class,LoginModule.class})
public interface LoginComponent {

    LoginPresenter getLoginPresenter();

}

