package com.starnet.drysistertest.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.starnet.drysistertest.MainActivity;
import com.starnet.drysistertest.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn;
    private Button regBtn;
    private EditText accountEdt;
    private EditText passwordEdt;
    private CheckBox rememberPass;
    SqlAccountPassword sqlAccountPassword;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login_btn);
        regBtn = (Button) findViewById(R.id.reg_btn);
        accountEdt = (EditText) findViewById(R.id.edit_account);
        passwordEdt = (EditText) findViewById(R.id.edit_pass);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        sqlAccountPassword = new SqlAccountPassword();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_pass",false);
        if (isRemember) {
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdt.setText(account);
            passwordEdt.setText(password);
            rememberPass.setChecked(true);
        }

        loginBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        sqlAccountPassword.CreateDatabase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                login();
                break;

            case R.id.reg_btn:
                register();
                break;

            default:
                break;

        }
    }

    public void login() {
        String account = accountEdt.getText().toString();
        String password = passwordEdt.getText().toString();
        /**查询数据库是否存在这个账号和密码*/
        boolean bExiste = sqlAccountPassword.ValidateLogin(account,password);
        if (bExiste) {
            editor = pref.edit();
            if (rememberPass.isChecked()) {
                editor.putBoolean("remember_pass",true);
                editor.putString("account",account);
                editor.putString("password",password);
            } else {
                editor.clear();
            }
            editor.apply();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            accountEdt.setText("");
            passwordEdt.setText("");
            Toast.makeText(this,"账号和密码不正确",Toast.LENGTH_LONG).show();
        }
    }

    public void register(){
        String account = accountEdt.getText().toString();
        String password = passwordEdt.getText().toString();
        /**查询数据库是否存在这个账号和密码*/
        boolean bExiste = sqlAccountPassword.ValidateRegister(account) ;
        if (bExiste) {
            Toast.makeText(this,"账号已存在",Toast.LENGTH_LONG).show();
        } else {
            boolean isOk = sqlAccountPassword.AddDatabase(account, password);
            if (isOk) {
                Toast.makeText(this,"账号注册成功",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"账号注册失败",Toast.LENGTH_LONG).show();
            }
        }
    }
}
