package example.hellofragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private Button mSearchBtn;
	private EditText mEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSearchBtn = (Button) findViewById(R.id.search_btn);
		mEditText = (EditText) findViewById(R.id.keyword);
		
		mSearchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = mEditText.getText().toString();
				if (!TextUtils.isEmpty(keyword)) {
					Intent intent = new Intent(MainActivity.this, ResultActivity.class);
					intent.putExtra(Intent.EXTRA_TEXT, keyword);
					startActivity(intent);
				}
			}
		});
	}
}
