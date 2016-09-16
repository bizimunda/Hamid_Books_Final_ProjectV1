package evoliris.com.hamid_books_final_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import evoliris.com.hamid_books_final_project.R;
import evoliris.com.hamid_books_final_project.app.LoginApplication;
import evoliris.com.hamid_books_final_project.model.AppUser;

/**
 * Created by temp on 15/09/2016.
 */
public class LoginActivity extends ActionBarActivity {

    private LoginApplication application;

    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLoginSubmit;
    private Button btnLoginSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        application= (LoginApplication) getApplication();

        etLoginUsername= (EditText) findViewById(R.id.et_login_username);
        etLoginPassword= (EditText) findViewById(R.id.et_login_password);
        btnLoginSubmit=(Button)findViewById(R.id.btn_login_login);
        btnLoginSignup=(Button)findViewById(R.id.btn_login_signup);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found=false;

                String userName=etLoginUsername.getText().toString();
                String password=etLoginPassword.getText().toString();

                for(AppUser user: application.getUsers()){
                    if(userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                        found=true;
                        break;
                    }
                }
                if(found){
                    Intent toMainIntent= new Intent(LoginActivity.this, MainActivity.class);
                    toMainIntent.putExtra("username", userName);
                    startActivity(toMainIntent);

                }else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignupIntent= new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(toSignupIntent, SignupActivity.REQUEST_CODE);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SignupActivity.REQUEST_CODE:

                switch (resultCode){
                    case RESULT_OK:

                        Bundle extras= data.getExtras();
                        etLoginUsername.setText(extras.getString("username"));
                        etLoginPassword.setText(extras.getString("password"));

                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(LoginActivity.this, "Cancelled !", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Log.e(getLocalClassName(), "Unknown resultCode: " + resultCode);
                        break;
                }

                break;
            default:
                Log.e(getLocalClassName(),"Unknown request code: "+requestCode);
        }
    }



}
