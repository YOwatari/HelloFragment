package example.hellofragment;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	private TextView mTextView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		mTextView = (TextView) findViewById(R.id.result);
		
		Intent intent = getIntent();
		String keyword = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (!TextUtils.isEmpty(keyword)) {
			Uri.Builder builder = new Uri.Builder();
			builder.scheme("http");
			builder.encodedAuthority("connpass.com");
			builder.path("/api/v1/event/");
			builder.appendQueryParameter("keyword", keyword);
			
			searchEvents(builder.build().toString());
		}
	}

	private void searchEvents(String query) {
		AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
			private static final String ERROR = "can not search";
			private ProgressDialog mDialog;
			
			@Override
			protected void onPreExecute() {
				mDialog = new ProgressDialog(ResultActivity.this);
				mDialog.setMessage("Searching...");
				mDialog.show();
				super.onPreExecute();
			}
			
			@Override
			protected String doInBackground(String... params) {
				if (params.length < 1)
					return ERROR;
				if (TextUtils.isEmpty(params[0]))
					return ERROR;
				
				String url = params[0];
								
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet request = new HttpGet(url);
				
				try {
				    String result = httpClient.execute(request, new ResponseHandler<String>() {
				        @Override
				        public String handleResponse(HttpResponse response)
				                throws ClientProtocolException, IOException {
				        	
				            switch (response.getStatusLine().getStatusCode()) {
				            case HttpStatus.SC_OK:
				                return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				            case HttpStatus.SC_NOT_FOUND:
				                return ERROR;
				            default:
				                return ERROR + response.getStatusLine().getStatusCode();
				            }
				        }
				    });
				    
				    return result;
				} catch (ClientProtocolException e) {
					return ERROR;
				} catch (IOException e) {
					return ERROR;
				} finally {
				    httpClient.getConnectionManager().shutdown();
				}
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (mDialog != null)
					mDialog.dismiss();
				if (mTextView != null) {
					mTextView.setText(result);
				}
				
				super.onPostExecute(result);
			}
			
		};
		
		task.execute(query);
	}
}
