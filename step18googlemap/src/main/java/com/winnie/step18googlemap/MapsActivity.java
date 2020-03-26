package com.winnie.step18googlemap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_maps);
        setContentView(R.layout.activity_my);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // activity_my.xml에 있는 SupportMapFragment 객체의 참조값 얻어내기
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // 지도가 동작할 준비가 완료됨을 감시할
        // 리스너 객체 등록하기
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // AlaMoana Center in Honolulu의 위도와 경도 정보를 가지고 있는 LatLng 객체
        // (* 위도 : Latitude, 경도 : Longitude)
        LatLng AlaMoana = new LatLng(21.2915249, -157.8518421);
        // 마커 옵션 객체 (내가 원하는 곳에 마커의 위치를 표시하는 것)
        MarkerOptions option=new MarkerOptions();
        option.position(AlaMoana);
        option.title("Ala Moana Center");
        // 지도상에 마커 표시하기
        mMap.addMarker(option);

        // 지정한 위치와 배율로 카메라 이동하기
        CameraUpdate cu=CameraUpdateFactory.newLatLngZoom(AlaMoana, 18);
        mMap.animateCamera(cu);
    }
}
