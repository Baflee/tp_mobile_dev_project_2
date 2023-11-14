package com.example.tp_final_sauce_algerienne_proj_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_final_sauce_algerienne_proj_2.model.LoginCredentials;
import com.example.tp_final_sauce_algerienne_proj_2.model.User;
import com.example.tp_final_sauce_algerienne_proj_2.remote.APIUtils;
import com.example.tp_final_sauce_algerienne_proj_2.remote.UserService;
import com.example.tp_final_sauce_algerienne_proj_2.util.BCrypt;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private UserService userService;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private Button signupButton;
    private Button loginButton;
    private Button validateButton;
    private boolean isSignUpActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        signupButton = (Button) findViewById(R.id.signupButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        validateButton = (Button) findViewById(R.id.validateButton);

        //UserService
        userService = APIUtils.getUserService();

        loginButton.setEnabled(false);
        signupButton.setEnabled(true);

        // Set up button click listeners
        loginButton.setOnClickListener(view -> {
            loginUI();
        });

        signupButton.setOnClickListener(view -> {
            signupUI();
        });

        validateButton.setOnClickListener(view -> {
            if (isSignUpActive) {
                attemptSignUp();
            } else {
                attemptLogin();
            }
        });
    }

    private void loginUI() {
        isSignUpActive = false;
        loginButton.setEnabled(false);
        signupButton.setEnabled(true);
        nameEditText.setVisibility(View.GONE); // Hide the name field
        validateButton.setEnabled(true);
        validateButton.setText("Validate Login"); // Set text to 'Login' for the validate button
    }

    private void signupUI() {
        isSignUpActive = true;
        loginButton.setEnabled(true);
        signupButton.setEnabled(false);
        nameEditText.setVisibility(View.VISIBLE); // Show the name field
        validateButton.setText("Validate Signup"); // Set text to 'Signup' for the validate button
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    // Validates password (example: at least 8 characters, at least one number)
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        if (password == null)
            return false;
        return pattern.matcher(password).matches();
    }

    private void attemptSignUp() {
        String email = emailEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if any field is empty
        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate email and password
        if (!isValidEmail(email)) {
            Toast.makeText(LoginActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(LoginActivity.this, "Password must be at least 8 characters and include a number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash the password
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Create a new user object
        User newUser = new User(email, name, encryptedPassword);

        // Make the network call to add the user
        Call<User> call = userService.addUser(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    loginUI();
                } else {
                    Toast.makeText(LoginActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("userSaved", MODE_PRIVATE);
        SharedPreferences.Editor myUser = sharedPreferences.edit();

        myUser.putInt("id", user.getId());
        myUser.putString("email", user.getEmail());
        myUser.putString("name", user.getName());
        // Add other user details you want to save
        myUser.apply();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate email and password
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        userService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    boolean isUserFound = false;
                    for (User user : users) {
                        if (user.getEmail().equalsIgnoreCase(email) && BCrypt.checkpw(password, user.getPassword())) {
                            isUserFound = true;
                            saveUserData(user); // Save user data in Shared Preferences
                            break;
                        }
                    }
                    if (isUserFound) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

