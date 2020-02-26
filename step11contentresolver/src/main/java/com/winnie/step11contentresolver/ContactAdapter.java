package com.winnie.step11contentresolver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private int layoutRes;
    private LayoutInflater inflater;
    private List<ContactDto> list;

    // 생성자
    public ContactAdapter(Context context, int layoutRes, List<ContactDto> list){
        // 필드에 필요한 값과 참조값을 넣어준다.
        this.context=context;
        this.layoutRes=layoutRes;
        // 레이아웃 전개자 객체
        inflater=LayoutInflater.from(context);
        this.list=list;
    }

    @Override
    public int getCount() {
        // 모델의 갯수 리턴
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        // i번째 모델 리턴
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        // i번째 모델의 아이디
        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){         // 만일 View가 null이면
            // 레이아웃을 전개해서 cell View객체를 만든다
            view=inflater.inflate(layoutRes, viewGroup, false);
        }
        // cell View에 contactDto에 있는 정보를 출력하고
        ContactDto dto=list.get(i);
        TextView text_id=view.findViewById(R.id.text_id);
        TextView text_phone=view.findViewById(R.id.text_phone);
        TextView text_name=view.findViewById(R.id.text_name);
        text_id.setText(Integer.toString(dto.getId()));
        text_phone.setText(dto.getPhone());
        text_name.setText(dto.getName());

        // cell View를 리턴해준다.
        return view;
    }
}
