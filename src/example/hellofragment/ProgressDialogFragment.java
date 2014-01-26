package example.hellofragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ProgressDialogFragment extends DialogFragment {
	
	public final static String TITLE = "Title";
	public final static String MESSAGE = "Message";
	public final static String MAX = "Max";
	
	private static ProgressDialog progressDialog;
	
	
	static ProgressDialogFragment newInstance() {
		ProgressDialogFragment f = new ProgressDialogFragment();
		
		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceBundle) {
		if (progressDialog != null)
			return progressDialog;
		
		setCancelable(false);
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle(getArguments().getString(TITLE));
		progressDialog.setMessage(getArguments().getString(MESSAGE));
		progressDialog.setIndeterminate(true);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//progressDialog.setMax(getArguments().getInt(MAX));
		//progressDialog.setProgress(0);
		return progressDialog;
	}
	
	public void updateProgress(int value) {
		if (progressDialog != null)
			progressDialog.setProgress(value);
	}
	
	@Override
	public Dialog getDialog() {
		return progressDialog;
	}
}
