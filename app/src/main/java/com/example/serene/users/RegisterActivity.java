package com.example.serene.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.serene.R;
import com.example.serene.dashboards.UserDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference ref;
    ProgressDialog pd;
    //FirebaseDatabase firebaseDatabase;

    Button reg_btn,goto_login;
    TextInputLayout fullname, username, email, cellphone, password;
    ImageView img_logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        //Views
        reg_btn = findViewById(R.id.reg);
        goto_login = findViewById(R.id.goto_login);
        fullname = findViewById(R.id.full_name);
        username = findViewById(R.id.user_name);
        cellphone = findViewById(R.id.cell_phone);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        img_logo = findViewById(R.id.img_logo_2);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setTitle("Registering...");
                pd.setMessage("Please wait as we register you");
                pd.show();

                String str_fullname = fullname.getEditText().getText().toString();
                String str_username = username.getEditText().getText().toString();
                String str_cellphone = cellphone.getEditText().getText().toString();
                String str_email = email.getEditText().getText().toString();
                String str_password = password.getEditText().getText().toString();

                if (TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_cellphone) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    pd.setTitle("ERROR");
                    pd.setMessage("Please enter all data");

                }
                else if(str_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must have more than 6 Characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(str_fullname, str_username, str_cellphone, str_email, str_password);
                }

            }
        });





    }

    private void registerUser(String str_fullname, String str_username, String str_cellphone, String str_email, String str_password) {

        auth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String Uid = firebaseUser.getUid();

                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Uid", Uid);
                    hashMap.put("Fullname", str_fullname);
                    hashMap.put("Username", str_username);
                    hashMap.put("Cellphone", str_cellphone);
                    hashMap.put("Email", str_email);
                    hashMap.put("Password", str_password);

                    ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                pd.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, UserDashboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "Sorry Failed To Register You", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();

            }
        });

    }


}//Keep for clas