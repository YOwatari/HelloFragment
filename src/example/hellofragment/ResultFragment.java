package example.hellofragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment implements LoaderCallbacks<String> {
	
	private TextView mTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceBundle) {
		View v = inflater.inflate(R.layout.result, container, false);
		mTextView = (TextView) v.findViewById(R.id.result);
		return v;
	}

	@Override
	public Loader<String> onCreateLoader(int arg0, Bundle arg1) {
		// 非同期処理Loader生成
		Log.d("debug", "onCreateLoader");
		String data = arg1.getString("url");
		return new DownloadJsonAsyncTaskLoader(getActivity(), data);
	}

	@Override
	public void onLoadFinished(Loader<String> arg0, String arg1) {
		// 非同期処理完了時
		if (mTextView != null)
			mTextView.setText(arg1);
	}

	@Override
	public void onLoaderReset(Loader<String> arg0) {
		// 非同期処理キャンセル時

	}
}
