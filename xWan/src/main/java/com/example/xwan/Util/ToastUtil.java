package com.example.xwan.Util;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xwan.R;
import com.example.xwan.common.MApplication;

/**
 * 传说中的吐司
 * 
 * @author yangyoutao
 * 
 */
public class ToastUtil {
	private static Toast toast;
	private static TextView mText;

	public static void showToast(String str) {
		if (toast == null) {
			toast = new Toast(MApplication.getIntance());
			toast.setDuration(Toast.LENGTH_SHORT);
			View view = View.inflate(MApplication.getIntance(),
					R.layout.layout_toast, null);
			mText = (TextView) view.findViewById(R.id.toast_content);
			toast.setView(view);
			mText.setText(str);
		} else {
			mText.setText(str);
		}
		toast.show();

	}

}
