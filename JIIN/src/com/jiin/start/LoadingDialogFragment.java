package com.jiin.start;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;

import com.jiin.R;


public class LoadingDialogFragment extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Dialog dialog = new Dialog(getActivity());
	      // 제목 숨기기
	      dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	      // 전체 화면
	      dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
	      // 배경을 투명하게하기
	      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

	      dialog.setContentView(R.layout.dialog_loading);
	      
	      return dialog;
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		
	}

	
}