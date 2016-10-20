package ratio.com.marvelQ.Login;

import org.json.JSONException;
import org.json.JSONObject;

import ratio.com.marvelQ.api.AsyncResponse;
import ratio.com.marvelQ.lib.EventBus;
import ratio.com.marvelQ.api.spiderWeb;

/**
 * Created by FranciscoPR on 17/10/16.
 */
public class LoginRepositoryImpl implements LoginRepository, AsyncResponse {

    spiderWeb request;
    EventBus eventBus;

    public LoginRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void attemptLogin(String mail, String password) {
        request = new spiderWeb(this,"POST");
        String parameters="email="+mail+"&password="+password;
        request.execute(LoginEvent.LOGIN+"","login",parameters);
        //postEvent(null,LoginEvent.LOGIN,true);
    }



    @Override
    public void attemptFBLogin(String name, String email, String oauth) {
        request = new spiderWeb(this,"POST");
        String parameters="name="+name+"&email="+email+"&oauth="+oauth;
        request.execute(LoginEvent.LOGINFB+"","loginfb",parameters);
    }

    @Override
    public void attemptRegister(String name, String mail, String password) {
        //request.execute(LoginEvent.REGISTER+"");
        //postEvent(null,LoginEvent.REGISTER,true);
        request = new spiderWeb(this,"POST");
        String parameters="name="+name+"&email="+mail+"&password="+password;
        request.execute(LoginEvent.LOGINFB+"","register",parameters);
    }

    @Override
    public void processFinish(String output, int type,boolean success) {
        if(success){
            try {
                JSONObject jsonObject = new JSONObject(output);
                String token = jsonObject.getJSONObject("response").getString("token");
                postEvent(token, type, success);
            } catch (JSONException e) {
                e.printStackTrace();
                postError(type,"Error");
            }
        }
        else {
            postError(type,output);
        }

    }

    private void postError(int type,String error) {
        LoginEvent event = new LoginEvent();
        event.setType(type);
        event.setSuccess(false);
        event.setError(error);
        eventBus.post(event);
    }

    private void postEvent(String token, int type, boolean success)
    {
        LoginEvent event = new LoginEvent();
        event.setType(type);
        event.setSuccess(success);
        event.setToken(token);
        eventBus.post(event);
    }
}
