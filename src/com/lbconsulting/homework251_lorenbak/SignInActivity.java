package com.lbconsulting.homework251_lorenbak;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private EditText txtEmail = null;
	private EditText txtPassword = null;
	private CheckBox ckShowPassword = null;
	private Button btnSignIn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the Action bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sign_in);

		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPassword = (EditText) findViewById(R.id.txtPassword);

		ckShowPassword = (CheckBox) findViewById(R.id.ckShowPassword);
		ckShowPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// checkbox status is changed from uncheck to checked.
				int cursorStart = txtPassword.getSelectionStart();
				int cursorEnd = txtPassword.getSelectionEnd();

				if (!isChecked) {
					// show password
					txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
				} else {
					// hide password
					txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}
				txtPassword.setSelection(cursorStart, cursorEnd);
			}
		});

		btnSignIn = (Button) findViewById(R.id.btnSignIn);
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txtEmail.setError(null);
				txtPassword.setError(null);
				if (validEmail()) {
					if (validPassword()) {
						DisplayEmailAndPassword();
					}
				}
			}
		});

	}

	private boolean validEmail() {
		boolean result = false;
		String email = txtEmail.getText().toString().trim();
		if (email.length() > 0) {
			Pattern p = Pattern.compile("^(?i)[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
			Matcher m = p.matcher(email);
			result = m.matches();
		}
		if (!result) {
			txtEmail.setError(getString(R.string.error_invalid_email_address));
		}
		return result;

	}

	private boolean validPassword() {
		// Passwords must be at least 8 characters long and 
		// contain at least one upper case, one lower case, and one numeric character.
		boolean result = false;
		String password = txtPassword.getText().toString().trim();

		if (password.length() < 8) {
			txtPassword.setError(getString(R.string.error_password_to_short));
			return result;
		}

		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(password);
		if (!m.find()) {
			txtPassword.setError(getString(R.string.error_password_has_no_numbers));
			return result;
		}

		p = Pattern.compile("[a-z]+");
		m = p.matcher(password);
		if (!m.find()) {
			txtPassword.setError(getString(R.string.error_password_has_no_lower_case_letters));
			return result;
		}
		p = Pattern.compile("[A-Z]+");
		m = p.matcher(password);
		result = m.find();
		if (!result) {
			txtPassword.setError(getString(R.string.error_password_has_no_uppper_case_letters));
		}
		return result;
	}

	private void DisplayEmailAndPassword() {
		Intent resultsIntent = new Intent(this, ResultsActivity.class);
		resultsIntent.putExtra("email", txtEmail.getText().toString().trim());
		resultsIntent.putExtra("password", txtPassword.getText().toString().trim());
		startActivity(resultsIntent);
	}

}
