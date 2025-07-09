package com.example.myapplicationsplash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationsplash.api.ApiClient
import com.example.myapplicationsplash.api.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {

    private lateinit var inputField: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputField = findViewById(R.id.input)
        loginButton = findViewById(R.id.btn_login)

        loginButton.setOnClickListener {
            val employeeId = inputField.text.toString().trim()

            if (employeeId.isEmpty()) {
                Toast.makeText(this, "Please enter your Employee ID", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = ApiClient.apiService.login(LoginRequest(employeeId))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Login, response.message, Toast.LENGTH_SHORT).show()

                            // Optional: Save token or navigate
                            val prefs = getSharedPreferences("pfms_prefs", MODE_PRIVATE)
                            prefs.edit().putString("jwt_token", response.token).apply()

                            // startActivity(Intent(this@Login, MainActivity::class.java))
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Login, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
