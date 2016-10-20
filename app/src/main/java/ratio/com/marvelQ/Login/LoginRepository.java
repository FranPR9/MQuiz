package ratio.com.marvelQ.Login;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public interface LoginRepository {

    void attemptLogin(String mail , String password);
    void attemptRegister(String name, String mail , String password);
    void attemptFBLogin(String name, String email, String oauth);
}
