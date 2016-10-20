package ratio.com.marvelQ.Login;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public class LoginInteractorImpl implements LoginInteractor {

    LoginRepository repository;

    public LoginInteractorImpl(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeLogin(String email, String password) {
        repository.attemptLogin(email, password);
    }

    @Override
    public void executeRegister(String name, String email, String password) {
        repository.attemptRegister(name, email, password);
    }

    @Override
    public void executefbloginValidation(String name, String email, String oauth) {
        repository.attemptFBLogin(name, email, oauth);
    }
}
