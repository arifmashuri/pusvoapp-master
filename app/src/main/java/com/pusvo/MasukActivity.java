package com.pusvo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MasukActivity extends AppCompatActivity {
    public void onBackPressed() {

    }
    ProgressDialog progress;
    Context mContext;
    SharedPrefManager sharedPrefManager;
    Boolean passwordhidden=false;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnLogin3) Button btnLogin3;
    @BindView(R.id.btnRegister) Button btnRegister;
    @BindView(R.id.lihatpassword) ImageView lihatpass;
//    @BindView(R.id.loginButton)  LoginButton loginButton;


    @OnClick(R.id.lupausername) void lupausername() {
        startActivity(new Intent(MasukActivity.this, LupaUsernameActivity.class));
    }
    @OnClick(R.id.lupapassword) void lupapassword() {
        startActivity(new Intent(MasukActivity.this, LupaPasswordActivity.class));
    }
    @OnClick(R.id.lihatpassword) void lihatpassword() {
        if(passwordhidden) {
            //etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            lihatpass.setImageResource(R.drawable.ic_eye);
            passwordhidden=false;
            etPassword.setSelection(etPassword.getText().length());
        }
        else {
            //etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            lihatpass.setImageResource(R.drawable.ic_eyeslash);
            passwordhidden=true;
            etPassword.setSelection(etPassword.getText().length());
        }

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 999) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);


        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //Toast.makeText(mContext, account.getEmail(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(mContext, account.getIdToken(), Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.

            progress = ProgressDialog.show(mContext, null, "Sedang memuat...", true, false);
            Sprite doubleBounce = new ThreeBounce();
            doubleBounce.setColor(R.color.bg_colordark);
            progress.setIndeterminateDrawable(doubleBounce);
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            String SHA1 = Enkripsi.access_key(randomUUIDString);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<Userinfo> call = api.masukgoogle(account.getEmail(), account.getIdToken(), randomUUIDString, SHA1);
            call.enqueue(new Callback<Userinfo>() {
                @Override
                public void onResponse(Call<Userinfo> call, retrofit2.Response<Userinfo> response) {
                    if (response.isSuccessful()) {
                        String nama = response.body().getNama();
                        String username = response.body().getUsername();
                        final String api_key = response.body().getApi_key();
                        final String bearer = response.body().getBearer();

                     //   progress.dismiss();


                        Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, username);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_API_KEY, api_key);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_BEARER, bearer);
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                        FirebaseMessaging.getInstance().subscribeToTopic("news");

                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        if (!task.isSuccessful()) {
                                            return;
                                        }

                                        // Get new Instance ID token
                                        String token = task.getResult();
//                                                    String refreshedToken = FirebaseInstanceId.getInstance().getId();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(URL)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        API api = retrofit.create(API.class);
                                        Call<Userinfo> call = api.kirimtoken(api_key, token);
                                        call.enqueue(new Callback<Userinfo>() {
                                                         @Override
                                                         public void onResponse(Call<Userinfo> call, Response<Userinfo> response) {

                                                         }

                                                         @Override
                                                         public void onFailure(Call<Userinfo> call, Throwable t) {
                                                             t.printStackTrace();
                                                             //progress.dismiss();

                                                         }
                                                         // Log and toast
                                                     }
                                        );
                                    }
                                });


                        startActivity(new Intent(mContext, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }
                    else {
                        // Jika login gagal
//                                String message = null;
//                                try {
//                                    message = response.errorBody().string();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();


                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            String errorMsg = jObjError.getString("pesan");
                            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                   // progress.dismiss();

                }

                @Override
                public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(MasukActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                //    progress.dismiss();
                }
            });
            finish();
        } catch (ApiException e) {

            Toast.makeText(mContext, "Gagal login "+e, Toast.LENGTH_SHORT).show();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setPermissions(Arrays.asList("email","public_profile"));

        ButterKnife.bind(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("764817079788-ccbba1k7752qr0jv489f79n3nl1n9che.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        btnLogin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 999);
            }
                                    });



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {



                                    progress = ProgressDialog.show(mContext, null, "Sedang memuat...", true, false);
                                    Sprite doubleBounce = new ThreeBounce();
                                    doubleBounce.setColor(R.color.bg_colordark);
                                    progress.setIndeterminateDrawable(doubleBounce);
                                    UUID uuid = UUID.randomUUID();
                                    String randomUUIDString = uuid.toString();
                                    String SHA1 = Enkripsi.access_key(randomUUIDString);
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    API api = retrofit.create(API.class);
                                    Call<Userinfo> call = api.masukfacebook(object.getString("email"), loginResult.getAccessToken().getToken(), randomUUIDString, SHA1);
                                    call.enqueue(new Callback<Userinfo>() {
                                        @Override
                                        public void onResponse(Call<Userinfo> call, retrofit2.Response<Userinfo> response) {
                                            if (response.isSuccessful()) {
                                                String nama = response.body().getNama();
                                                String username = response.body().getUsername();
                                                final String api_key = response.body().getApi_key();
                                                final String bearer = response.body().getBearer();

                                                //   progress.dismiss();


                                                Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, username);
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_API_KEY, api_key);
                                                sharedPrefManager.saveSPString(SharedPrefManager.SP_BEARER, bearer);
                                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                                FirebaseMessaging.getInstance().subscribeToTopic("news");

                                                FirebaseMessaging.getInstance().getToken()
                                                        .addOnCompleteListener(new OnCompleteListener<String>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<String> task) {
                                                                if (!task.isSuccessful()) {
                                                                    return;
                                                                }

                                                                // Get new Instance ID token
                                                                String token = task.getResult();
//                                                    String refreshedToken = FirebaseInstanceId.getInstance().getId();
                                                                Retrofit retrofit = new Retrofit.Builder()
                                                                        .baseUrl(URL)
                                                                        .addConverterFactory(GsonConverterFactory.create())
                                                                        .build();
                                                                API api = retrofit.create(API.class);
                                                                Call<Userinfo> call = api.kirimtoken(api_key, token);
                                                                call.enqueue(new Callback<Userinfo>() {
                                                                                 @Override
                                                                                 public void onResponse(Call<Userinfo> call, Response<Userinfo> response) {

                                                                                 }

                                                                                 @Override
                                                                                 public void onFailure(Call<Userinfo> call, Throwable t) {
                                                                                     t.printStackTrace();
                                                                                     //progress.dismiss();

                                                                                 }
                                                                                 // Log and toast
                                                                             }
                                                                );
                                                            }
                                                        });


                                                startActivity(new Intent(mContext, MainActivity.class)
                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                                finish();

                                            }
                                            else {
                                                // Jika login gagal
//                                String message = null;
//                                try {
//                                    message = response.errorBody().string();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();


                                                JSONObject jObjError = null;
                                                try {
                                                    jObjError = new JSONObject(response.errorBody().string());
                                                    String errorMsg = jObjError.getString("pesan");
                                                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                             progress.dismiss();

                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {

                                            t.printStackTrace();
                                            Toast.makeText(MasukActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                                                progress.dismiss();
                                        }
                                    });






                                } catch(JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });











                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), R.string.dibatalkan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplication(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        });




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().length() >= 7 && etPassword.getText().toString().length() >= 7) {
                    progress = ProgressDialog.show(mContext, null, "Sedang memuat...", true, false);
                    Sprite doubleBounce = new ThreeBounce();
                    doubleBounce.setColor(R.color.bg_colordark);
                    progress.setIndeterminateDrawable(doubleBounce);
                    UUID uuid = UUID.randomUUID();
                    String randomUUIDString = uuid.toString();
                    String SHA1 = Enkripsi.access_key(randomUUIDString);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    API api = retrofit.create(API.class);
                    Call<Userinfo> call = api.masuk(etUsername.getText().toString(), etPassword.getText().toString(), randomUUIDString, SHA1);
                    call.enqueue(new Callback<Userinfo>() {
                        @Override
                        public void onResponse(Call<Userinfo> call, retrofit2.Response<Userinfo> response) {
                            if (response.isSuccessful()) {
                                String nama = response.body().getNama();
                                String username = response.body().getUsername();
                                final String api_key = response.body().getApi_key();
                                final String bearer = response.body().getBearer();

                                progress.dismiss();


                                    Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, username);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_API_KEY, api_key);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_BEARER, bearer);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                    FirebaseMessaging.getInstance().subscribeToTopic("news");

                                FirebaseMessaging.getInstance().getToken()
                                        .addOnCompleteListener(new OnCompleteListener<String>() {
                                            @Override
                                            public void onComplete(@NonNull Task<String> task) {
                                                if (!task.isSuccessful()) {
                                                    return;
                                                }

                                                // Get new Instance ID token
                                                String token = task.getResult();
//                                                    String refreshedToken = FirebaseInstanceId.getInstance().getId();
                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(URL)
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();
                                                API api = retrofit.create(API.class);
                                                Call<Userinfo> call = api.kirimtoken(api_key, token);
                                                call.enqueue(new Callback<Userinfo>() {
                                                                 @Override
                                                                 public void onResponse(Call<Userinfo> call, Response<Userinfo> response) {

                                                                 }

                                                                 @Override
                                                                 public void onFailure(Call<Userinfo> call, Throwable t) {
                                                                     t.printStackTrace();


                                                                 }
                                                                 // Log and toast
                                                             }
                                                );
                                            }
                                        });


                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                            }
                            else {
                                // Jika login gagal
//                                String message = null;
//                                try {
//                                    message = response.errorBody().string();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();


                                JSONObject jObjError = null;
                                try {
                                    jObjError = new JSONObject(response.errorBody().string());
                                    String errorMsg = jObjError.getString("pesan");
                                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                            progress.dismiss();

                        }

                        @Override
                        public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(MasukActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    });
                }

            else {
                Toast.makeText(MasukActivity.this, "Username atau password belum lengkap", Toast.LENGTH_SHORT).show();


            }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pusvo.com/daftar"));
                startActivity(browserIntent);
            }
        });


        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(MasukActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }








}
