package com.winnie.step07customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.logging.LogRecord;

public class TouchView extends View {
    // 이 View의 폭과 높이
    private int width, height;
    // 글자 혹은 도형을 출력할 때 사용할 Paint 객체
    private Paint bluePaint, yellowPaint;
    // 이벤트의 종류를 저장할 필드 (일단 선언만 할 경우 null이다.)
    private String eventType="";
    // 이벤트가 일어난 곳의 좌표를 저장할 필드
    private int eventX, eventY;

    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*
        이 View가 처음 구성될 때 최초 한번 호출되고 그 이후에는 View의 사이즈가 바뀌었을 때 호출된다.
        즉, 화면의 사이즈가 바뀐다는 것은 사용자가 이 앱을 모바일 환경에서 가로로 볼지, 세로로 볼지,
        또는 태블릿 PC로 볼 지 모르는 상황이다.
        View가 차지하고 있는 폭과 높이가 전달된다.
        이 값을 사용하고싶으면 필드에 저장해놓는다.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 크기를 필드에 저장하고
        width=w;
        height=h;
        // 초기화 메소드를 호출한다.
        init();
    }

    // 초기화하는 메소드
    public void init(){
        // 파란 Paint 객체 설정
        bluePaint=new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setTextSize(50);

        // 노란 Paint 객체 설정
        yellowPaint=new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStyle(Paint.Style.FILL);
        yellowPaint.setStrokeWidth(5);

        // 핸들러가 동작하도록 빈 메시지 보내기
        handler.sendEmptyMessageDelayed(0, 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 이벤트의 종류 출력하기
        canvas.drawText("Type of Event : "+eventType, 0, 100, bluePaint);
        // 이벤트가 일어난 곳의 좌표 출력하기
        canvas.drawText("x:"+eventX+" y:"+eventY, 0, 200, bluePaint);
        // View의 폭과 높이
        canvas.drawText("width:"+width+" height:"+height, 0, 300, bluePaint);
        // 이벤트가 일어난 곳에 원 그리기
        canvas.drawCircle(eventX, eventY, 100, yellowPaint);
        // 이벤트가 일어난 곳에 직선 그리기
        canvas.drawLine(0, eventY, 2000, eventY, yellowPaint);
        canvas.drawLine(eventX, 0, eventX, 2000, yellowPaint);
    }

    // View에 터치 이벤트가 일어나면 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        eventX=(int)event.getX();
        eventY=(int)event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                eventType="ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                eventType="ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                eventType="ACTION_UP";
                break;
        }
        return true;        // 모든 이벤트(ACTION_UP, DOWN, MOVE)를 처리
            // false를 리턴하면 ACTION_DOWN 이벤트만 처리된다. (ACTION_UP과 ACTION_MOVE는 안 됨.)
    }

    // 가만히 있어도 업데이트 되도록
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {    // 새로운 스레드라고 생각하면 된다.
            invalidate();           // 화면 갱신하기
            // 10/1000초 이후에 핸들러에 빈 메시지 보내기
            handler.sendEmptyMessageDelayed(0, 10);
        }
    };
}
