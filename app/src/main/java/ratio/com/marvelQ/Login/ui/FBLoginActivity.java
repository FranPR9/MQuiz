package ratio.com.marvelQ.Login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ratio.com.marvelQ.Login.LoginPresenter;
import ratio.com.marvelQ.Login.di.LoginComponent;
import ratio.com.marvelQ.R;
import ratio.com.marvelQ.Score.ui.MainActivity;
import ratio.com.marvelQ.entities.AeSimpleSHA1;
import ratio.com.marvelQ.marvelQApp;

public class FBLoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = "LoginView";

    private String name = "";
    private String emailstr = "";
    private String img;
    private LoginButton loginButton;
    private LoginPresenter presenter;
    private LoginComponent component;
    private CallbackManager callbackManager;

    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.email_tilayout)
    TextInputLayout emailTilayout;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.pass_tilayout)
    TextInputLayout passTilayout;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.register)
    Button register;
    @Bind(R.id.login_progress)
    ProgressBar loginProgress;
    @Bind(R.id.name)
    EditText nametxt;
    @Bind(R.id.name_tilayout)
    TextInputLayout nameTilayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fblogin);
        ButterKnife.bind(this);


        //AppEventsLogger.activateApp(this);
        setInjection();
        presenter.onCreate();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { //callbackmanager maneja el login de facebook
            @Override
            public void onSuccess(LoginResult loginResult) { //todo bien, onSuccess
                // App code
                RequestFB(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        checkAlreadyConnected();

    }

    private void setInjection() {
        marvelQApp app = (marvelQApp) getApplication();
        component = app.getLoginComponent(this, this);
        presenter = component.getLoginPresenter();
    }

    public void RequestFB(final AccessToken accessToken) {
        //graphresquest te entrega email nombre apellido birthday gender y foto
        Log.d("successlogin", "login successed");
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.v("LoginActivity", response.toString()+" "+object.toString());
                        try {
                            //Intent i = new Intent(FBLoginActivity.this, MainActivity.class); //empieza la actividad
                            emailstr = object.getString("email");
                            name =  object.getString("name");
                            String names[] = name.split(" ");
                            name = names[0];
                            Log.d(TAG,name);
                            img = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            String oauth = object.getString("id");
                            String pw_hash = "";

                                pw_hash = AeSimpleSHA1.SHA1(oauth);

                            presenter.fbloginValidation(name,emailstr,pw_hash);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            showLoginFBError();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                            showLoginFBError();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            showLoginFBError();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday,picture");

        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void checkAlreadyConnected() {
        Profile actualprofile = Profile.getCurrentProfile();
        if (actualprofile != null)//clase de facebook
        {
            //Prueba si ya hay un login, empieza mainActivity, manda imagen, nombre y apellido etc

            Log.d("connected", "user already made login!");
            img = actualprofile.getProfilePictureUri(100, 100).toString();
            name = actualprofile.getFirstName();
            Log.d(TAG,name);
            String oauth = actualprofile.getId();
            String pw_hash = "";

            try {
                pw_hash = AeSimpleSHA1.SHA1(oauth);
                presenter.fbloginValidation(name,"",pw_hash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                showLoginFBError();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                showLoginFBError();
            }


        }
    }


    @Override
    public void showSuccess() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError() {
        Toast.makeText(this, getString(R.string.loginError), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMissingInfoError() {
        Toast.makeText(this, getString(R.string.missingInfoError), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enableInputs() {
        enableAllInputs(true);
    }

    private void enableAllInputs(boolean b) {
        email.setEnabled(b);
        password.setEnabled(b);
        login.setEnabled(b);
        register.setEnabled(b);
        loginButton.setEnabled(b);
    }

    @Override
    public void disableInputs() {
        enableAllInputs(false);
    }

    @Override
    public void showProgress() {
        loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRegisterError() {
        Toast.makeText(this, getString(R.string.registerError), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toNextActivity(String token) {

        Intent i = new Intent(FBLoginActivity.this, MainActivity.class);
        if (img != null)
            i.putExtra("img", img);
        i.putExtra("name", name);
        i.putExtra("token", token);
        enableInputs();
        startActivity(i);
    }

    @Override
    public void showLoginFBError() {
        Toast.makeText(this, getString(R.string.fbloginError), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.login)
    public void Login() {
        disableInputs();
        showProgress();
        if (!InputsError()) {
            String pw_hash = "";

            try {
                pw_hash = AeSimpleSHA1.SHA1(password.getText().toString());
                presenter.loginValidation(email.getText().toString(),pw_hash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                showLoginError();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                showLoginError();
            }

        } else {
            enableInputs();
            hideProgress();
        }
    }

    boolean isregister_click = false;

    @OnClick(R.id.register)
    public void Register() {

        if (isregister_click) {
            disableInputs();
            showProgress();
            if (!ErroOnName()&&!InputsError()) {
                String pw_hash = "";

                try {
                    pw_hash = AeSimpleSHA1.SHA1(password.getText().toString());
                    presenter.registerValidation(name, email.getText().toString(), pw_hash);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    showLoginError();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    showLoginError();
                }

            } else {
                enableInputs();
                hideProgress();
            }
        } else {
            isregister_click = true;
            nameTilayout.setVisibility(View.VISIBLE);
        }

    }

    private boolean ErroOnName() {
        nameTilayout.setError(null);
        String namestr = nametxt.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check id empty
        if (TextUtils.isEmpty(namestr)) {
            nameTilayout.setError(getString(R.string.empty_name));
            focusView = nameTilayout;
            cancel = true;
        }

        if(!cancel)
        {
            name =  namestr;
        }else{
            focusView.requestFocus();
        }
        return cancel;

    }

    private boolean InputsError() {

        emailTilayout.setError(null);
        passTilayout.setError(null);

        // Store values at the time of the login attempt.
        String emailstr = email.getText().toString();
        String passwordstr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordstr)) {// && !isPasswordValid(passwordstr)) {
            passTilayout.setError(getString(R.string.empty_password));
            focusView = passTilayout;
            cancel = true;
        }


        if (TextUtils.isEmpty(emailstr)) {
            emailTilayout.setError(getString(R.string.empty_email));
            focusView = emailTilayout;
            cancel = true;
        }
        // Check for a valid email address.
        if (!isValidEmail(emailstr)) {
            emailTilayout.setError(getString(R.string.invalid_email));
            focusView = emailTilayout;
            cancel = true;
        }

        if (cancel)
            focusView.requestFocus();

        return cancel;


    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
