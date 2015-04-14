package trikita.obsqr;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import butterknife.*;
import android.content.DialogInterface;

public class ObsqrActivity extends Activity implements CameraPreview.OnQrDecodedListener {

	private final static String tag = "ObsqrActivity";
	@InjectView(R.id.surface) CameraPreview mCameraPreview;
	private String mLastKnownContent = "";
	private AlertDialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_camera);
		ButterKnife.inject(this);
		mCameraPreview.setOnQrDecodedListener(this);
	}

	@Override
	public void onQrDecoded(String s) {
		if (mLastKnownContent.equals(s))
			return;
		mLastKnownContent = s;

		// parse QR content string
		final QrContent mQrContent = QrContent.from(this, s);

		// show dialog with QR content
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(mQrContent.getTitle());
		builder.setMessage(mQrContent.getText());
			String action = mQrContent.getAction();
		builder.setPositiveButton(mQrContent.getAction(),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mQrContent.action();
					}
				});
		builder.setNegativeButton(R.string.dlg_alert_cancel_btn_caption,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		if (mDialog != null)
			mDialog.cancel();
		mDialog = builder.create();
		mDialog.show();
	}

	@Override
	public void onQrNotFound() {
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(tag, "onResume()");
		
		boolean success = mCameraPreview.acquireCamera(getWindowManager()
			.getDefaultDisplay().getRotation());
		if (!success) {
			openAlertDialog();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(tag, "onPause()");

		mCameraPreview.releaseCamera();
	}

	/** 
	 * Sets up alert dialog views and presents the dialog to user
	 * when camera could not be opened
	 */
	private void openAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.dlg_alert_msg))
			.setCancelable(false)
			.setPositiveButton(getString(R.string.dlg_alert_ok_btn_caption),
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					ObsqrActivity.this.finish();
					dialog.dismiss();
				}
			});
		AlertDialog alert = builder.create();	
		alert.show();
	}
}
