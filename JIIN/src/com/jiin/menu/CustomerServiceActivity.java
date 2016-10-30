package com.jiin.menu;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;

public class CustomerServiceActivity extends ActionBarActivity{
	
	public static final int REQUEST_CODE_CROP = 0;
	public static final int RESULT_OK = -1;
	File mSavedFile;
	
	String inquiryType, userEmail, content, date , addFiletext2;
	String picture = "";
	EditText emailView, contentView, dateView;
	LinearLayout qType;
	TextView qTypetext, addFilePath;
	LinearLayout addFile;
	ImageView addFileCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customerservice);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		qTypetext = (TextView)findViewById(R.id.text_Qtype);
		qType = (LinearLayout)findViewById(R.id.layout_Qtype);
		qType.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceActivity.this);
				builder.setTitle("문의유형");
				final String[] array = getResources().getStringArray(R.array.qType);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strText = array[which];
						qTypetext.setText(strText);
						inquiryType=strText;
					}
				});
				builder.create().show();
			}
		});
		
		emailView = (EditText)findViewById(R.id.edit_emailp);
		contentView = (EditText)findViewById(R.id.edit_problem_content);
		dateView = (EditText)findViewById(R.id.edit_problem_date);
		addFile = (LinearLayout)findViewById(R.id.addFile);
		addFile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				photoPickerIntent.setType("image/*");
				photoPickerIntent.putExtra("crop", "true");
				photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
				photoPickerIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());				
				startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP);
			}
		});
		if (savedInstanceState != null) {
			String file = savedInstanceState.getString("filename");
			if (file != null) {
				mSavedFile = new File(file);
			}
		}
		addFilePath= (TextView)findViewById(R.id.text_addFile);
		
		addFileCheck = (ImageView)findViewById(R.id.image_addFile);
		if(picture.equals("")){
			addFileCheck.setImageResource(R.drawable.clip_111);
			addFileCheck.setClickable(false);
		}
		
		
		
		
		Button btnComplete = (Button)findViewById(R.id.btn_sendproblem);
		btnComplete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//동의에 체크하게하기
				
				userEmail = emailView.getText().toString();
				content = contentView.getText().toString();
				date = dateView.getText().toString();
				
				Log.i("email", userEmail);
				
				try {
					NetworkManager.getInstnace().putInquiry(CustomerServiceActivity.this, inquiryType, content, date, userEmail, picture, new OnResultListener<CustomerServiceData>() {

						@Override
						public void onSuccess(CustomerServiceData result) {
							if(result.result.equals("success")){
								Toast.makeText(CustomerServiceActivity.this, "문의 메일이 전송되었습니다", Toast.LENGTH_SHORT).show();
								finish();
							}else if(result.result.equals("fail")){
								Toast.makeText(CustomerServiceActivity.this, result.message, Toast.LENGTH_SHORT).show();
							}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(CustomerServiceActivity.this, code +"", Toast.LENGTH_SHORT).show();
						}
						
					} );
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}
	
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CROP) {
			Bitmap bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
			//addFile.setImageBitmap(bm);
			picture = mSavedFile.getAbsolutePath();
			addFilePath.setText(picture);
			addFileCheck.setImageResource(R.drawable.xxxxxxxx);
			addFileCheck.setClickable(true);
			
			addFileCheck.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					picture = "";
					addFilePath.setText("첨부 파일");
					addFileCheck.setImageResource(R.drawable.ic_clip);
					addFileCheck.setClickable(false);
				}
			});
		}
			
	}

    
    private Uri getTempUri() {
		mSavedFile = new File(Environment.getExternalStorageDirectory(),"temp_" + System.currentTimeMillis()/1000 + ".jpg");
		return Uri.fromFile(mSavedFile);
	}
    
    @Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mSavedFile != null) {
			outState.putString("filename", mSavedFile.getAbsolutePath());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		if(id == android.R.id.home){
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
