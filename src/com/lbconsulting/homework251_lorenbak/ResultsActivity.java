package com.lbconsulting.homework251_lorenbak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ResultsActivity extends Activity {
	private TextView tvEmail = null;
	private TextView tvPassword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the Action bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_display_results);

		tvEmail = (TextView) findViewById(R.id.tvEmail);
		tvPassword = (TextView) findViewById(R.id.tvPassword);

		Intent intent = getIntent();
		String email = intent.getStringExtra("email");
		String password = intent.getStringExtra("password");

		tvEmail.setText("Email: " + email);
		tvPassword.setText("Password: " + password);
	}
}
