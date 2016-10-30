package com.jiin.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jiin.NetworkManager;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.R;
import com.jiin.User;
import com.jiin.myprofile.qna.QnaData;
import com.jiin.myprofile.qna.QnaListItem;

public class MyQNAFragment extends Fragment {

	User user;
	ListView listView;
	MyQnaAdapter mAdapter;
	QnaListItem qnaDatas;
	int position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_qna, container, false);

		mAdapter = new MyQnaAdapter(getActivity());
		listView = (ListView) view.findViewById(R.id.list_qna);
		listView.setEmptyView(view.findViewById(R.id.text_empty));

		listView.setAdapter(mAdapter);

		initData();

		return view;
	}
	
	
	private QnaListItem getOtherItem(String qna_id) {
		for (QnaListItem item : user.qna) {
			if (item.question_id.equals(qna_id)) {
				return item;
			}
		}
		return null;
	}

	private void initData() {
		
		NetworkManager.getInstnace().getQnaList(getActivity(), null, new OnResultListener<QnaData>(){
			@Override
			public void onSuccess(QnaData result) {
				if(result.result.equals("success")){
					
					mAdapter.addAll(result.datas);
					
				}else if(result.result.equals("fail")){
					Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), code+"", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}