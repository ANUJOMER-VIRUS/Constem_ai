package com.omer.constems_ai_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
MaterialButton signin;
GoogleSignInClient googleSignInClient;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin=findViewById(R.id.siginButton);
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("682518859356-b6dnattms064u3ajr77rqqtsjicbqpbf.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(MainActivity.this,googleSignInOptions);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=googleSignInClient.getSignInIntent();
                startActivityForResult(intent
                ,100);
            }
        });
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            startActivity(new Intent(MainActivity.this,HomePage.class))
            ;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
if(signInAccountTask.isSuccessful()){
    String s="Google sign in sucessful";
    displayToast(s);
    try {
        GoogleSignInAccount googleSignInAccount=signInAccountTask.getResult(ApiException.class);
        if(googleSignInAccount!=null){
            AuthCredential authCredential= GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(MainActivity.this, HomePage.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        displayToast("FireBase Authsuccessful");

                    }
                    else {
                        displayToast("Auth Failed"+task.getException());
                    }
                }
            });

        }
    } catch (ApiException e) {
        throw new RuntimeException(e);
    }
}
        }
    }

    private void displayToast(String s) {
        Log.d("error", "displayToast: "+s);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}