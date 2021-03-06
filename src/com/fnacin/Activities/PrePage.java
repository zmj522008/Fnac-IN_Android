package com.fnacin.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.fnacin.pojo.ParameterInfo;
import com.fnacin.service.AuthentificationService;
import com.fnacin.Activities.R;

public class PrePage extends Activity {

	private WebView prepage = null;
	private Button contineBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prepage);

		ParameterInfo parameterInfo = AuthentificationService.getInstance()
				.getAuthentificationInfo().getParameterInfo();
		prepage = (WebView) findViewById(R.id.prepage);
		prepage.loadData(parameterInfo.getPrePage(), "text/html", "utf-8");

		contineBtn = (Button) this.findViewById(R.id.btnContinue);
		contineBtn
				.setVisibility(parameterInfo.getIsMandatory() == 0 ? View.VISIBLE
						: View.INVISIBLE);

		contineBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						MainPage.class));
				finish();
			}
		});

	}

}
