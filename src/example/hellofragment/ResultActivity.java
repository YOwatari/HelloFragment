package example.hellofragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

public class ResultActivity extends FragmentActivity{
	
	private static final int LOADER_ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_fragment);
		
		Intent intent = getIntent();
		String keyword = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (!TextUtils.isEmpty(keyword)) {
			Uri.Builder builder = new Uri.Builder();
			builder.scheme("http");
			builder.encodedAuthority("connpass.com");
			builder.path("/api/v1/event/");
			builder.appendQueryParameter("keyword", keyword);
			
			FragmentManager manager = getSupportFragmentManager();
			ResultFragment fragment = (ResultFragment) manager.findFragmentById(R.id.result_fragment);
			
			Bundle bundle = new Bundle();
			bundle.putString("url", builder.build().toString());
			
			getSupportLoaderManager().initLoader(LOADER_ID, bundle, fragment);
		}
	}
}
