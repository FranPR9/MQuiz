package ratio.com.marvelQ.Login;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public interface LoginInteractor {
    void executeLogin(String email, String password);
    void executeRegister(String name, String email, String password);
    void executefbloginValidation(String name, String email, String oauth);
}
