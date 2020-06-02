package com.example.tmaadminapp.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tmaadminapp.Presenters.LoginPresenter;
import com.example.tmaadminapp.Views.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenterImplementer implements LoginPresenter {
    public static final String DB_KEY = "saveEmailAndPassword";
    private LoginView loginView;
    private SharedPreferences saveUserNameAndPassword;
    private SharedPreferences.Editor editor;
    private final String USER_EMAIL_KEY = "userEmail", USER_PASSWORD_KEY = "userPassword";
    private Context context;

    public LoginPresenterImplementer(LoginView loginView , Context context) {
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void login(final DatabaseReference dbRef, final FirebaseAuth mAuth, final String email, String password)
    {
        if (mAuth != null && !email.isEmpty() && !password.isEmpty()) {
            loginView.showProgressBar();

            // login with email and password method
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        if (email.equals("admin@gmail.com")) // if admin then move to admin home page
                        {
                            loginView.moveToMainPage();
                            loginView.hideProgressBar();
                            loginView.showMessage("Successfully login");
                        }
                        else
                            {
                            String currentUser = mAuth.getCurrentUser().getUid(); // get the current worker head id
                            Query query = dbRef.child(currentUser);

                            // get the worker head role or department
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    // get the department of worker head
                                    String role = dataSnapshot.child("department").getValue().toString();
                                    Log.d("roleOfUser", "onDataChange: " + role);

                                    /**
                                     * check department and move to his home page
                                     */
                                    if (role.equals("Sanitation")) {
                                        loginView.goToSanitationHomePage();
                                        loginView.hideProgressBar();
                                        loginView.showMessage("Successfully login");
                                    }
                                    else if(role.equals("Infrastructure"))
                                    {
                                        loginView.goToInfraHomePage();
                                        loginView.hideProgressBar();
                                        loginView.showMessage("Successfully login");
                                    }
                                    else if(role.equals("Union Council"))
                                    {
                                        loginView.goToUnionCouncilHomePage();
                                        loginView.hideProgressBar();
                                        loginView.showMessage("Successfully login");
                                    }
                                    else if(role.equals("Regulation"))
                                    {
                                        loginView.goToRegulationHomePage();
                                        loginView.hideProgressBar();
                                        loginView.showMessage("Successfully login");
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {}
                            });

                        }

                    }
                    else
                    {
                        loginView.showMessage(String.valueOf(task.getException()));
                        loginView.hideProgressBar();
                    }

                }
            });


        } else {
            loginView.showMessage("Please enter email and password");
        }

    }

    @Override
    public void savePassword(String userEmail, String userPassword) {
        // save password in shared pref
        saveUserNameAndPassword = context.getSharedPreferences(DB_KEY, Context.MODE_PRIVATE);
        editor = saveUserNameAndPassword.edit();

        editor.putString(USER_EMAIL_KEY, userEmail);
        editor.putString(USER_PASSWORD_KEY, userPassword);
        editor.apply();

    }

    @Override
    public void getUserNameAndPasswordFromSharedPref()
    {
        // get user name and password from share pref
        saveUserNameAndPassword = context.getSharedPreferences(DB_KEY, Context.MODE_PRIVATE);
        String saveEmail = saveUserNameAndPassword.getString(USER_EMAIL_KEY, "");
        String savePassword = saveUserNameAndPassword.getString(USER_PASSWORD_KEY, "");

        loginView.getSaveUserEmailAndPassword(saveEmail, savePassword);

    }
}
