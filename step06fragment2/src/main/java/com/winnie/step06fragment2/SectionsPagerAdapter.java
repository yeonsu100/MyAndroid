package com.winnie.step06fragment2;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    // 스트링 리소스
    //@StringRes
    // 이미지 리소스 아이디를 int[]에 미리 준비해두기
    //private int[] imgs = {R.drawable.usa, R.drawable.korea, R.drawable.japan,
    //                                 R.drawable.france, R.drawable.belgium};
    // private final Context mContext;          -- 필요없어진 context는 지워버리고
    // ViewPager에 출력할 모델
    private List<CountryDto> countries;

    // 생성자
    public SectionsPagerAdapter(List<CountryDto> countries, FragmentManager fm) {  // 생성자의 인자로 context와 FragmentManager를 전달받는다.
        super(fm);
        // mContext = context;
        this.countries=countries;
    }

    // 인자로 전달하는 인덱스에 해당하는 Fragment 객체의 참조값을 리턴해주는 메소드
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // position 인덱스에 해당하는 이미지 리소스 아이디
        // int resId=imgs[position];
        // position 인덱스에 해당하는 CountryDto
        CountryDto dto=countries.get(position);
        // position 인덱스에 해당하는 프래그먼트의 참조값을 얻어와서
        // PlaceholderFragment fr=PlaceholderFragment.newInstance(resId);
        PlaceholderFragment fr=PlaceholderFragment.newInstance(dto);
        // 리턴해주기
        return fr;
    }

    // 인자로 전달되는 인덱스에 해당하는 문자열(페이지 제목, tab 제목)을 리턴해주는 메소드
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {      // * CharSequence : String의 부모타입이므로 String type이라 생각하면 된다.
//        String title=Integer.toString(position+1);
//        return title;
        String title=countries.get(position).getName();
        return title;
    }

    // 전체 페이지의 갯수를 리턴해준다.
    @Override
    public int getCount() {
        // Show 5 total pages.
        // return 5;
        // 전체 국가(모델)의 갯수 리턴
        return countries.size();
    }
}