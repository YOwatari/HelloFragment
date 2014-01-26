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

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

public class DownloadJsonAsyncTaskLoader extends AsyncTaskLoader<String> {
	
	private String mArg;
	private String mData;
	
	private static final String ERROR = "can not search";
	
	public DownloadJsonAsyncTaskLoader(Context context, String arg) {
		super(context);
		this.mArg = arg;
	}
	
	@Override
	public String loadInBackground() {
		String[] params = new String[1];
		
		params[0] = mArg;
		
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
	protected void onStartLoading() {
		if (mData != null) 
			deliverResult(mData);
		
		if (takeContentChanged() || mData == null)
			forceLoad();
	}
	
	@Override
	protected void onStopLoading() {
		cancelLoad();
	}
	
	@Override
	public void deliverResult(String data) {
		if (isReset())
			return;
		mData = data;
		super.deliverResult(mData);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		onStopLoading();
		mData = null;
	}

}
