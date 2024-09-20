package com.example.bustime.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bustime.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeTableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private WebView timetableWebView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeTableFragment newInstance(String param1, String param2) {
        TimeTableFragment fragment = new TimeTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timetableWebView = view.findViewById(R.id.timetable_webview);
        timetableWebView.getSettings().setUseWideViewPort(true); // wide viewport를 유연하게 설정하고
        timetableWebView.getSettings().setLoadWithOverviewMode(true); // 컨텐츠가 웹뷰 범위에 벗어날 경우  크기에 맞게 조절
        timetableWebView.setWebViewClient(new WebViewClient());
        timetableWebView.getSettings().setJavaScriptEnabled(true); //자바스크립트 사용 여부
        timetableWebView.getSettings().setSupportZoom(true); // 화면 줌 사용 여부
        timetableWebView.getSettings().setBuiltInZoomControls(true); //화면 확대 축소 사용 여부
        timetableWebView.getSettings().setDisplayZoomControls(true); //화면 확대 축소시, webview에서 확대/축소 컨트롤 표시 여부
        timetableWebView.loadUrl("https://www.andong.go.kr/synap/skin/doc.html?fn=FILE_000000000534115_0&rs=/synap/BBS/FILE_000000000534115_0/");
//        timetableWebView.loadUrl("https://www.andong.go.kr/synap/skin/doc.html?fn=FILE_000000000534115_0&rs=/synap/BBS/FILE_000000000534115_0/");

    }
}