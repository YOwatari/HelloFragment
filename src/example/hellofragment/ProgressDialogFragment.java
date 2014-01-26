package example.hellofragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ProgressDialogFragment extends DialogFragment {
	
	public static ProgressDialogFragment newInstance(int title) {
		ProgressDialogFragment frag = new ProgressDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceBundle) {
		ProgressDialog dialog = new ProgressDialog((ResultActivity)getActivity());
		
		dialog.setMessage("searching...");
		dialog.setProgress(ProgressDialog.STYLE_SPINNER);
		dialog.show();
		
		return dialog;
	}
}
