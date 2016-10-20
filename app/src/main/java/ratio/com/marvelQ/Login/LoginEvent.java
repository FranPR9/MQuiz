package ratio.com.marvelQ.Login;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public class LoginEvent {

    private boolean success;
    private String error;
    private String token;

    private int type;

    static public final int REGISTER=1;
    static public final int LOGIN=2;
    static public final int LOGINFB=6;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
