package ratio.com.marvelQ.Login;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public interface LoginPresenter {

    void onCreate();
    void onDestroy();


    void loginRespond(LoginEvent event);

    void loginValidation(String email, String password);
    void registerValidation(String name,String email, String password);
    void fbloginValidation(String name, String email, String oauth);

}
