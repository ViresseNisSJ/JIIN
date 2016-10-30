package com.jiin.start;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiin.KeepData;
import com.jiin.NetworkManager;
import com.jiin.R;
import com.jiin.NetworkManager.OnResultListener;


public class MissDialogFragment extends DialogFragment {
	
	EditText emailView;
	Button btn_send;
	
	Dialog dialog;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		  dialog = new Dialog(getActivity());
	      // ���� �����
	      dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	      // ��ü ȭ��
	      dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
	      // ����� �����ϰ��ϱ�
	      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

	      dialog.setContentView(R.layout.dialog_misspw);
	      
	      emailView = (EditText)dialog.findViewById(R.id.edit_emailforpw);
	      btn_send = (Button)dialog.findViewById(R.id.btn_sendemail);
	      btn_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userEmail = emailView.getText().toString();
				NetworkManager.getInstnace().putFindPassword(getActivity(), userEmail, new OnResultListener<KeepData>() {

					@Override
					public void onSuccess(KeepData result) {
						if(result.result.equals("success")){
							Toast.makeText(getActivity(), "�ش� �̸��Ϸ� �ӽ� ��й�ȣ�� �����߽��ϴ�", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}else if(result.result.equals("fail")){
							Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
						}
						
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), code +"", Toast.LENGTH_SHORT).show();
						
					}
					
				} );
			}
		});
	      
	      return dialog;
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		
	}

	
}