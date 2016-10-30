package com.jiin.myprofile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.start.LoadingDialogFragment;


public class BasicFragment extends Fragment {
	
	public static final int REQUEST_CODE_CROP_BACKGROUND = 0;
	public static final int REQUEST_CODE_CROP_PROFILE = 1;
	public static final int REQUEST_CODE_CROP_PICTURE = 2;
	public static final int RESULT_OK = -1;
	File mSavedFile, mSavedFile2, mSavedFile3;
	
	String nickName, job, university, mainPicture, background, area, age2;
	int age;
	ArrayList<Pictures> pictures = new ArrayList<Pictures>();
	int count = 0;
	ArrayList<String> allpictures = new ArrayList<String>();
	
	TextView nickname;
	EditText nicknameView;
	EditText ageView;
	EditText areaView;
	EditText jobView;
	EditText univView;
	ImageView profileView, backView;
	GridView picturesView;
	
	BasicAdapter mAdapter;
	
	
	LoadingDialogFragment dialog;
	
	
	
	public BasicFragment() {
		this.setHasOptionsMenu(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
    	View view= inflater.inflate(R.layout.fragment_basicinfo, container, false);
    	
    	mAdapter = new BasicAdapter();
//    	InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//    	imm.hideSoftInputFromWindow(EditText.getWindowToken(), 0);
    	
    	nicknameView = (EditText)view.findViewById(R.id.edit_info_nickname);
    	String nick = nicknameView.getText().toString();
    	ageView = (EditText)view.findViewById(R.id.edit_info_age);
    	areaView = (EditText)view.findViewById(R.id.edit_info_area);
    	jobView = (EditText)view.findViewById(R.id.edit_info_job);
    	univView = (EditText)view.findViewById(R.id.edit_info_univ);
    	
    	profileView = (ImageView)view.findViewById(R.id.image_profile);
    	profileView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("프로필사진");
				final String[] array = getResources().getStringArray(R.array.myphoto2);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch(which){
						case 0:
							Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							photoPickerIntent.setType("image/*");
							photoPickerIntent.putExtra("crop", "true");
							photoPickerIntent.putExtra("aspectX", 270);
							photoPickerIntent.putExtra("aspectY", 360);
							photoPickerIntent.putExtra("scale", true);
							photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP_PROFILE);
							break;
							
						case 1:
							Intent photoPickerIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							photoPickerIntent2.putExtra("crop", "ture");
							photoPickerIntent2.putExtra("aspectX", 270);
							photoPickerIntent2.putExtra("aspectY", 360);
							photoPickerIntent2.putExtra("scale", true);
							photoPickerIntent2.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							photoPickerIntent2.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent2, REQUEST_CODE_CROP_PROFILE);
							
						}
						
					}
				});
				if (savedInstanceState != null) {
					String file = savedInstanceState.getString("filename");
					if (file != null) {
						mSavedFile = new File(file);
					}
				}
				builder.create().show();
			}
		});
    	backView = (ImageView)view.findViewById(R.id.image_background);
    	backView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("배경사진");
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
							photoPickerIntent.putExtra("aspectX", 360);
							photoPickerIntent.putExtra("aspectY", 270);
							photoPickerIntent.putExtra("scale", true);
							photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri2());
							photoPickerIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP_BACKGROUND);
							break;
							
						case 1:
							Intent photoPickerIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							photoPickerIntent2.putExtra("crop", "true");
							photoPickerIntent2.putExtra("aspectX", 360);
							photoPickerIntent2.putExtra("aspectY", 270);
							photoPickerIntent2.putExtra("scale", true);
							photoPickerIntent2.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri2());
							photoPickerIntent2.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());				
							startActivityForResult(photoPickerIntent2, REQUEST_CODE_CROP_BACKGROUND);
						
						}
						
					}

				});
				if (savedInstanceState != null) {
					String file = savedInstanceState.getString("filename");
					if (file != null) {
						mSavedFile2 = new File(file);
					}
				}
				builder.create().show();
			}
		});
    	
    	LinearLayout btnCertify = (LinearLayout)view.findViewById(R.id.btn_certify);
    	
    	btnCertify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),CertifyActivity.class));
			}
		});
    	
    	/*사진 여러장 추가*/
    	
    	picturesView = (GridView)view.findViewById(R.id.grid_pics);
    	picturesView.setAdapter(mAdapter);
    	picturesView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				if(position==0){
//					startActivity(new Intent(getActivity(), PhotoGuideActivity.class));
				}else if(position==1){
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
								photoPickerIntent.putExtra("aspectX", 270);
								photoPickerIntent.putExtra("aspectY", 360);
								photoPickerIntent.putExtra("scale", true);
								photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri3());
								photoPickerIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());				
								startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP_PICTURE);
								break;
								
							case 1:
								Intent photoPickerIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
								photoPickerIntent2.putExtra("crop", "true");
								photoPickerIntent2.putExtra("aspectX", 270);
								photoPickerIntent2.putExtra("aspectY", 360);
								photoPickerIntent2.putExtra("scale", true);
								photoPickerIntent2.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri3());
								photoPickerIntent2.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());				
								startActivityForResult(photoPickerIntent2, REQUEST_CODE_CROP_PICTURE);
							
							}
							
						}

					});
					if (savedInstanceState != null) {
						String file = savedInstanceState.getString("filename");
						if (file != null) {
							mSavedFile2 = new File(file);
						}
					}
					builder.create().show();
				}else {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("사진");
					final String[] array = getResources().getStringArray(R.array.myphoto3);
					builder.setItems(array, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch(which){
							case 0:
								Bitmap bm = BitmapFactory.decodeFile(allpictures.get(position-2));
								profileView.setImageBitmap(bm);
								mainPicture = allpictures.get(position-2);
								break;
							case 1:
								pictures.remove(position-2);
								allpictures.remove(position-2);
								mAdapter.addAll(pictures);
								count--;
							}
						}
					});
				}
			}
    		
		});
    	
    	initData();
    	
    	return view ;
    }
 

	private void initData() {
		Pictures mp1 = new Pictures();
		mp1.pics="";
		mp1.type=0;
		mAdapter.add(mp1);
		
		Pictures mp2 = new Pictures();
		mp1.pics="";
		mp1.type=1;
		mAdapter.add(mp2);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if(requestCode == REQUEST_CODE_CROP_PROFILE) {
				Bitmap bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
				profileView.setImageBitmap(bm);
				mainPicture = mSavedFile.getAbsolutePath();
				
			} else if (requestCode == REQUEST_CODE_CROP_BACKGROUND) {
				Bitmap bm2 = BitmapFactory.decodeFile(mSavedFile2.getAbsolutePath());
				backView.setImageBitmap(bm2);
				background = mSavedFile2.getAbsolutePath();
			
			}else if(requestCode == REQUEST_CODE_CROP_PICTURE){
				Pictures pi = new Pictures();
				pi.pics = mSavedFile3.getAbsolutePath();
				allpictures.add(mSavedFile3.getAbsolutePath());
				Log.i("allpictures", allpictures.get(count));
				pi.type = 2;
				pictures.add(pi);
				mAdapter.add(pi);
				count++;
			}
		}
			
	}

    
    private Uri getTempUri() {
		mSavedFile = new File(Environment.getExternalStorageDirectory(),"temp_" + System.currentTimeMillis()/1000 + ".jpg");
		return Uri.fromFile(mSavedFile);
	}
    
    private Uri getTempUri2() {
		mSavedFile2 = new File(Environment.getExternalStorageDirectory(),"temp_" + System.currentTimeMillis()/1000 + ".jpg");
		return Uri.fromFile(mSavedFile2);
	}

    private Uri getTempUri3() {
    	
    	mSavedFile3=new File(Environment.getExternalStorageDirectory(),"temp_" + System.currentTimeMillis()/1000 + ".jpg");
		return Uri.fromFile(mSavedFile3);
	}

    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mSavedFile != null) {
			outState.putString("filename", mSavedFile.getAbsolutePath());
			outState.putInt("count", count);
		}
		
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.myprofile, menu);
	}
	private LayoutInflater getMenuInflater() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		if (myToggle.onOptionsItemSelected(item)) {
//			return true;
//		}
		
		switch(item.getItemId()){		
		case R.id.menu_complete:
			nickName = nicknameView.getText().toString();
			age2 = ageView.getText().toString();
			area = areaView.getText().toString();
			job = jobView.getText().toString();
			university = univView.getText().toString();
			
			if(mainPicture.equals("")){
				Toast.makeText(getActivity(), "프로필 사진을 등록해 주세요", Toast.LENGTH_SHORT).show();
			}else{
				if(allpictures.size()<2){
					Toast.makeText(getActivity(), "사진은 두장 이상 등록해 주세요", Toast.LENGTH_SHORT).show();
				}else{
					if(age2.equals("")==false && area.equals("")==false && job.equals("")==false && university.equals("")==false && nickName.equals("")==false){
						dialog = new LoadingDialogFragment();
						dialog.setCancelable(false);
						dialog.show(getChildFragmentManager(), "loading");
						age = Integer.parseInt(age2);
						try {
							NetworkManager.getInstnace().putMyBasicInform(getActivity(), mainPicture, background, nickName, age, area, job, university, allpictures, new OnResultListener<BasicData>() {

								@Override
								public void onSuccess(BasicData result) {
									if(result.result.equals("success")){
										startActivity(new Intent(getActivity(), OptionActivity.class));
										getActivity().finish();
									}else if(result.result.equals("fail")){
										Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
									}
									dialog.dismiss();
								}

								@Override
								public void onFail(int code) {
									Toast.makeText(getActivity(), code+"", Toast.LENGTH_SHORT).show();
									dialog.dismiss();
								}
								
							} );
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							dialog.dismiss();
						}
						
						return true;
					}else{
						Toast.makeText(getActivity(), "필수 항목을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
						return true;
					}
				}
			}
			
		}

		return super.onOptionsItemSelected(item);
	}
}

	
