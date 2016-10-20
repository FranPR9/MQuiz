package ratio.com.marvelQ.Login.ui;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public interface LoginView {

    void showSuccess();
    void showLoginError();
    void showMissingInfoError();
    void enableInputs();
    void disableInputs();

    void showProgress();
    void hideProgress();

    void showRegisterError();

    void toNextActivity(String token);
    void showLoginFBError();

}