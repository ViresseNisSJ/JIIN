package com.jiin.myprofile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;

public class CertifyActivity extends ActionBarActivity {

	public static final int REQUEST_CODE_CROP = 0;
	public static final int REQUEST_CODE_CROP2 = 1;
	public static final int REQUEST_CODE_CROP3 = 2;
	public static final int RESULT_OK = -1;
	File mSavedFile;
	ArrayList<String> evidence = new ArrayList<String>();
	ImageView image1, image2, image3;
	TextView text1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_certify);
		
		/*액션바*/
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);//액션바에 있는 백키 onitemselected에서 조절
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.actionbar);
		
		text1 = (TextView)findViewById(R.id.text_html);
		text1.setText(Html.fromHtml("<b>출신학교</b> 혹은 <b>재직회사</b>에 대해 인증해주세요."));
//		text1.setMovementMethod(LinkMovementMethod.getInstance());
//		String source = getResources().getString(R.string.certify);
//		text1.setText(Html.fromHtml(source));
		
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, evidence);
		
		image1 = (ImageView)findViewById(R.id.image_certify1);
		image1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(CertifyActivity.this);
				builder.setTitle("사진");
				final String[] array = getResources().getStringArray(R.array.myphoto2);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch(which){
						case 0:
							Intent photoPickerIntent = new Intent(
									Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							photoPickerIntent.setType("image/*");
							photoPickerIntent.putExtra("crop", "true");
							photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent.putExtra("outputFormat",
									Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP);
							break;
						case 1:
							Intent photoPickerIntent2 = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							photoPickerIntent2.putExtra("crop", "circle");
							photoPickerIntent2.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent2.putExtra("outputFormat",
									Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent2, REQUEST_CODE_CROP);
						}
					}
				});
				
				builder.create().show();
			}
		});
		
		image2 = (ImageView)findViewById(R.id.image_certify2);
		image2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(CertifyActivity.this);
				builder.setTitle("사진");
				final String[] array = getResources().getStringArray(R.array.myphoto2);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch(which){
						case 0:
							Intent photoPickerIntent = new Intent(
									Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							photoPickerIntent.setType("image/*");
							photoPickerIntent.putExtra("crop", "true");
							photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent.putExtra("outputFormat",
									Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP2);
							break;
						case 1:
							Intent photoPickerIntent2 = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							photoPickerIntent2.putExtra("crop", "circle");
							photoPickerIntent2.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent2.putExtra("outputFormat",
									Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent2, REQUEST_CODE_CROP2);
						}
					}
				});
				
				builder.create().show();
			}
		});
		
		image3 = (ImageView)findViewById(R.id.image_certify3);
		image3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(CertifyActivity.this);
				builder.setTitle("사진");
				final String[] array = getResources().getStringArray(R.array.myphoto2);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch(which){
						case 0:
							Intent photoPickerIntent = new Intent(
									Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							photoPickerIntent.setType("image/*");
							photoPickerIntent.putExtra("crop", "true");
							photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent.putExtra("outputFormat",
									Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP3);
							break;
						case 1:
							Intent photoPickerIntent2 = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							photoPickerIntent2.putExtra("crop", "circle");
							photoPickerIntent2.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent2.putExtra("outputFormat",
									Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent2, REQUEST_CODE_CROP3);
						}
					}
				});
				
				builder.create().show();
			}
		});
		
		if (savedInstanceState != null) {
			String file = savedInstanceState.getString("filename");
			if (file != null) {
				mSavedFile = new File(file);
			}
		}
		
		Button btnSend = (Button)findViewById(R.id.btn_send);
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					NetworkManager.getInstnace().putEvidence(CertifyActivity.this, evidence, new OnResultListener<CertifyData>(){

						@Override
						public void onSuccess(CertifyData result) {
							finish();
						}

						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub
							
						}
						
					});
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
				

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			if(requestCode == REQUEST_CODE_CROP) {
				Bitmap bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
				image1.setImageBitmap(bm);
				evidence.add(mSavedFile.getAbsolutePath());
			}else if(requestCode == REQUEST_CODE_CROP2){
				Bitmap bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
				image2.setImageBitmap(bm);
				evidence.add(mSavedFile.getAbsolutePath());
			}else if(requestCode == REQUEST_CODE_CROP3){
				Bitmap bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
				image3.setImageBitmap(bm);
				evidence.add(mSavedFile.getAbsolutePath());
			}
			
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}