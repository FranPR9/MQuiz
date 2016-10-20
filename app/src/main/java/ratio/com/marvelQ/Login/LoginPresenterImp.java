package ratio.com.marvelQ.Login;

import org.greenrobot.eventbus.Subscribe;

import ratio.com.marvelQ.Login.ui.LoginView;
import ratio.com.marvelQ.lib.EventBus;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public class LoginPresenterImp implements LoginPresenter {

    private LoginView view;
    private EventBus eventBus;
    private LoginInteractor interactor;

    public LoginPresenterImp(LoginView view, EventBus eventBus, LoginInteractor interactor) {
        this.view = view;
        this.eventBus = eventBus;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    @Subscribe
    public void loginRespond(LoginEvent event) {
        view.hideProgress();
        if(event.isSuccess())
        {
            switch (event.getType()){
                case LoginEvent.LOGIN:
                    view.showSuccess();
                    view.toNextActivity(event.getToken());
                    break;
                case LoginEvent.LOGINFB:
                    view.showSuccess();
                    view.toNextActivity(event.getToken());
                    break;
                case LoginEvent.REGISTER:
                    view.showSuccess();
                    view.toNextActivity(event.getToken());
                    break;
                default:break;
            }
        }
        else{
            view.enableInputs();
            switch (event.getType()){
                case LoginEvent.LOGIN:
                    view.showLoginError();
                    break;
                case LoginEvent.REGISTER:
                    view.showRegisterError();
                    break;
                case LoginEvent.LOGINFB:
                    view.showLoginFBError();
                    break;
                default:break;
            }
        }

    }

    @Override
    public void loginValidation(String email, String password) {
        view.disableInputs();
        view.showProgress();
        interactor.executeLogin(email,password);
    }

    @Override
    public void registerValidation(String name,String email, String password) {
        view.disableInputs();
        view.showProgress();
        interactor.executeRegister(name, email,password);
    }

    @Override
    public void fbloginValidation(String name, String email, String oauth) {
        view.disableInputs();
        view.showProgress();
        interactor.executefbloginValidation(name, email, oauth);
    }
}
