package com.winnie.beer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BeerView extends View {
    // 뷰의 크기를 저장할 필드
    int viewWidth, viewHeight;
    // 맥주병 이미지를 저장할 필드 (캔버스에 그리고 싶으면 이미지를 비트맵으로 미리 만들어놔야 사용 가능하다)
    Bitmap bottleImg;
    // 맥주병의 크기
    int bottleWidth, bottleHeight;
    // MyView의 가운데 좌표
    int centerX, centerY;
    // canvas의 회전각도
    int angle=0;
    // action down이 일어난곳의 좌표를 저장할 필드
    int downX, downY;
    // action down이 일어난 시간을 저장할 필드
    long downTime;
    // 맥주병의 회전 각속도 (초 당 몇도씩 돌 것인지 결정 즉, 시간 당 도는 각도)
    int angleSpeed;
    // 상태값을 관리할 필드
    boolean isRotating=false;
    // 트릭 모드인지 여부
    boolean isTrick=false;
    // 트릭 모드 횟수
    int trickCount=0;
    // Context
    // Context context;
    // 사운드 매니저를 관리할 필드에
    Util.SoundManager sManager;
    // 재생할 음원을 관리할 static final 필드
    static final int SOUND_ZOMBIE=0;
    static final int SOUND_SHOOT=1;
    // 재생할 음원의 아이디를 저장할 필드
    Map<String,Integer> map=new HashMap<>();
    SoundPool pool;

    // 생성자
    public BeerView(Context context) {
        super(context);
        //this.context=context;
    }

    public BeerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //this.context=context;
    }

    // View의 사이즈를 전달받을 메소드
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth=w;
        viewHeight=h;
        // 비트맵 이미지 로딩하기 (원본크기)
        bottleImg= BitmapFactory.decodeResource(getResources(), R.drawable.bottle);
        // 크기 조절 (createScaledBitmap()을 이용해 원하는 사이즈를 지정할 수 있다)
        bottleImg=Bitmap.createScaledBitmap(bottleImg, 300, 600, false);
        // 로딩된 이미지의 폭과 높이
        bottleWidth=bottleImg.getWidth();
        bottleHeight=bottleImg.getHeight();
        // View의 정중앙 설정
        centerX=viewWidth/2;
        centerY=viewHeight/2;

        // 핸들러에 지연된 메세지 보내기
        handler.sendEmptyMessageDelayed(0,10);

        // 사운드 재생 준비
        // prepareSound();
        // 사운드 매니저의 참조값 얻어와서
        sManager=Util.SoundManager.getInstance();
        // 준비 작업을 하고
        sManager.init(getContext());
        // 필요한 사운드를 로딩 시킨다.
        sManager.addSound(SOUND_ZOMBIE, R.raw.zombie);
        sManager.addSound(SOUND_SHOOT, R.raw.shoot);
    }

//    public void prepareSound(){
//        pool= new SoundPool(5,      // 최대 동시 재생
//                AudioManager.STREAM_MUSIC, 0);
//        // 사운드 로딩과 동시에 사운드의 아이디값을 Map 에 저장하기
//        map.put(SOUND_ZOMBIE, pool.load(context, R.raw.zombie, 1));
//        map.put(SOUND_SHOOT, pool.load(context, R.raw.shoot, 1));
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        // 캔버스 회전시키기
        canvas.rotate(angle, centerX, centerY);

        canvas.drawBitmap(bottleImg,
                    centerX-bottleWidth/2,
                    centerY-bottleHeight/2,
                    null);

        // 회전 중인 경우
        if(angleSpeed>0){       // 앵글 속도가 0보다 클 때에만 움직이도록 (즉 시작부터 돌지 않도록) 조건문 추가
            // 각속도 만큼 회전 각도를 반영한다.
            angle += angleSpeed;            // 앵글의 속도를 일단 높인 뒤,
            angleSpeed--;                   // 속도를 점점 줄인다. (1씩 감소)
                // 트릭 심어놓기 (어떻게 움직이든 한 곳에 멈추게 된다. (트릭) - 세게 돌릴 때만 동작 (살짝 돌리면 적용 안됨))
                if(angleSpeed==80 && isTrick){     // 회전 속도가 80에서 멈추었을 때이면서 동시에 트릭 모드가 시행됐을 때
                    angle=0+90*trickCount;         // 초기값 0에서 트릭을 한번 수행할 때마다 90도씩 회전한다.
                    isTrick=false;                 // 트릭을 한번 사용했으면 트릭 모드를 해제한다.
                    trickCount++;
                }
        }else {                  // 회전이 끝난 경우
            if (isRotating) {
                // 효과음 재생시키기
                //pool.play(map.get(SOUND_ZOMBIE), 1, 1, 1, 0, 1);
                sManager.play(SOUND_ZOMBIE);
            }
            isRotating = false;
            angleSpeed = 0;
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            invalidate();
            handler.sendEmptyMessageDelayed(0,10);
        }
    };

    // 터치 이벤트 등록
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isRotating){         // 만일 회전 상태이면
            return false;       // 여기서 메소드 종료하기
        }
        // 이벤트가 일어난 곳의 좌표
        int x=(int)event.getX();
        int y=(int)event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 액션 다운 이벤트가 일어난 곳의 좌표와 시간을 필드에 저장하기
                downX=x;
                downY=y;
                downTime=System.currentTimeMillis();  // 1970년 1월 1일 자정을 기준으로 1/1000초씩 누적 증가
                break;
            case MotionEvent.ACTION_MOVE:             // (ACTION_MOVE가 진행되는 동안에는 할 일이 없다.)

                break;
            case MotionEvent.ACTION_UP:
                long upTime=System.currentTimeMillis();
                // action_down 이벤트와 action_up 이벤트가 일어나는데 걸린시간
                long deltaT=upTime-downTime;
                // 손가락의 속도 구하기
                double fingerSpeed=
                        Math.sqrt(Math.pow((x-downX) , 2)
                                +Math.pow((y-downY), 2))/deltaT;
                // 맥주병의 회전 각속도 부여하기
                angleSpeed=(int)fingerSpeed*50;
                // 회전 시작이라 표시
                isRotating=true;

                // 만일 좌하단 영역을 터치했을 때는 트릭 모드로 바꾼다.
            if(x<100 && y>viewHeight-100){
                isTrick=true;
            }
            break;
        }
        return true;
    }
}
