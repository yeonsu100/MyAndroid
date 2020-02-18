package com.winnie.step06fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/*
    [ Fragment 만드는 방법 ]
    1. Fragment 클래스를 상속받는다.
    2. onCreateView() 메소드를 오버라이딩 한다.
 */
public class MyFragment extends Fragment implements View.OnTouchListener {
    // 터치 횟수를 관리할 필드 (굳이 =0을 안써도 된다.)
    private int touchCount;
    private TextView textView;
    // private MainActivity activity;

    // 액티비티의 참조값을 MyFragmentListener type으로 사용하기
    private MyFragmentListener activity;

    // MyFragment를 사용할 액티비티가 구현할 리스너를 인터페이스
    public interface MyFragmentListener{
        public void showMessage(int count);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 인자로 전달되는 레이아웃 전개자 객체를 이용해 View 객체를 만들어서
        View view=inflater.inflate(R.layout.fragment_my, container);
        // TextView의 참조값 얻어오기 (필드에 저장해놓았던 참조값 사용)
        textView=view.findViewById(R.id.textView);
        // TextView에 터치리스너 등록하기
        textView.setOnTouchListener(this);
        // 해당 프래그먼트를 관리하는 액티비티의 참조값
        // (casting을 해야 사용할 수 있다 - 문제점 : MainActivity에 의존관계가 생기게 된다)
        // activity=(MainActivity) getActivity();
        // 해당 프래그먼트를 관리하는 액티비티의 참조값
        FragmentActivity a=getActivity();
        // 해당 액티비티가 MyFragmentListener type이 맞으면
        if(a instanceof MyFragmentListener){
            // MyFragmentListener type으로 casting한다.
            activity=(MyFragmentListener)a;
        }
        // view의 참조값을 리턴해준다.
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 1. 터치 카운드를 1 증가 시킨다.
        touchCount++;
        // 2. TextView에 출력하기 (int를 바로 집어넣으면 오류가 생기므로 String으로 저장한다.)
        textView.setText(Integer.toString(touchCount));
        // 3. 액티비티의 메소드 호출하면서 카운트 전달하기
        if(touchCount%10 == 0 && activity != null) {
            activity.showMessage(touchCount);
        }
        return false;
    }
}
