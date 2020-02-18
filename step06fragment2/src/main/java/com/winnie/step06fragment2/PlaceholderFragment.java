package com.winnie.step06fragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    // Fragment 객체를 생성해서 리턴해주는 static 메소드
    // public static PlaceholderFragment newInstance(int resId) {
    public static PlaceholderFragment newInstance(CountryDto dto) {
        PlaceholderFragment fr=new PlaceholderFragment();
        // Fragment에 전달할 Bundle 객체
        Bundle bundle=new Bundle();
        // bundle.putInt("resId", resId);
        bundle.putSerializable("dto", dto);
        // Fragment에 인자 전달하기
        fr.setArguments(bundle);
        return fr;
    }

    // 이미지 리소스 아이디를 담을 필드...는 필요없어졌으므로 지우고
    // private int resId;
    // 국가 정보를 담을 필드
    private CountryDto dto;

    // 1. Fragment가 최초 사용될 때 호출되는 메소드
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 인자로 Bundle 객체가 전달된다. (fr.setArguments(bundle);해서 전달했으므로 getArguments()로 읽어낸다.)
        // resId=getArguments().getInt("resId");
        dto=(CountryDto)getArguments().getSerializable("dto");
                    // ▲ Serializable type으로 리턴되는 것을 CountryDto type으로 캐스팅을 해 놓은 다음 저장한다.
    }

    // 2. Fragment가 활성화 될 때 마다 호출되는 메소드
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // res/layout/fragment_main.xml 문서를 전개해서 View 객체를 만든다.
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        // 이미지뷰의 참조값과
        ImageView imageView=view.findViewById(R.id.imageView);
        // TextView의 참조값 얻어와서
        TextView textView=view.findViewById(R.id.textView);
        // 이미지 출력하고
        // imageView.setImageResource(resId);
        imageView.setImageResource(dto.getResId());
        // 텍스트도 출력한 뒤
        textView.setText(dto.getContent());
        // View 객체 리턴해주기
        return view;
    }
}