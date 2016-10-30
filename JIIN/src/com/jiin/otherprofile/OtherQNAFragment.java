package com.jiin.otherprofile;

import android.content.Intent;
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

public class OtherQNAFragment extends Fragment {

	User user;
	ListView listView;
	OtherQnaAdapter mAdapter;
	OtherQnaItem qnaDatas;
	int position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_other_qna, container, false);

		Intent intent = getActivity().getIntent();
		user = (User) intent.getSerializableExtra("user");

		mAdapter = new OtherQnaAdapter(getActivity());
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
		NetworkManager.getInstnace().getQnaList(getActivity(), null, new OnResultListener<QnaData>() {

					@Override
					public void onSuccess(QnaData result) {
						if (result.result.equals("success")) {
							for (int i = 0; i < result.datas.size(); i++) {
								qnaDatas = new OtherQnaItem();
								QnaListItem item = result.datas.get(i);
								QnaListItem otheritem = getOtherItem(item.question_id);
								
								
								qnaDatas.question = item.question;

								/* 내 대답 */
								Answer ans = new Answer();
								if (item.myAnswer == -1 || otheritem == null) {
									ans.answer = "응답 없음";
									ans.matched = 0;
								} else {
									ans.answer = item.answers.get(item.myAnswer);
									ans.matched = 0;
									for (int j = 0; j < otheritem.wishAnswer
											.size(); j++) {
										if (otheritem.wishAnswer.get(j) == item.myAnswer) {
											ans.matched = 1;
											break;
										}
									}
								}
								qnaDatas.myAnswer = ans;

								/* 이성 대답 */
								Answer ans2 = new Answer();
								if (item.myAnswer == -1 || otheritem == null) {
									ans2.answer = "응답 없음";
									ans2.matched = 0;
								} else {
									ans2.answer = item.answers.get(otheritem.myAnswer);
									ans2.matched = 0;
									for (int j = 0; j < item.wishAnswer.size(); j++) {
										if (item.wishAnswer.get(j)==otheritem.myAnswer) {
											ans2.matched = 1;
											break;
										} 
									}
								}
								qnaDatas.yourAnswer = ans2;

								mAdapter.add(qnaDatas);
							}
							mAdapter.notifyDataSetChanged();

						} else {
							Toast.makeText(getActivity(), result.message,
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), code + "",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}