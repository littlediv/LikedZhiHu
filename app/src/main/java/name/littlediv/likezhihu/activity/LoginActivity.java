package name.littlediv.likezhihu.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import name.littlediv.likezhihu.R;

/**
 * Created by win7 on 2016/6/13.
 */
public class LoginActivity extends AppCompatActivity {


    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    AppCompatButton login;
    @Bind(R.id.singup)
    TextView singup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Create !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        login.setEnabled(false);

        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Authenticating...");
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onLoginSuccess();
                dialog.dismiss();
            }
        }, 3000);


    }

    private void onLoginSuccess() {
        login.setEnabled(true);
        finish();
    }

    private void onLoginFailed() {

        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        login.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String emails = email.getText().toString();
        String passwords = password.getText().toString();

        if (emails.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwords.isEmpty() || passwords.length() < 4 || passwords.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }
}
