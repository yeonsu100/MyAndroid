package com.winnie.step07customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/*
    [ Custom View 만드는 법 ]
    1. View 클래스를 상속받는다.
    2. Context 객체를 생성자의 인자로 전달받아서 부모 생성자에 전달하도록 한다.
    3. onDraw() 메소드를 오버라이딩해서 View화면을 구성한다.
 */
public class MyView extends View {                   // 1. View 클래스 상속
    // 필드 추가 - 터치이벤트가 일어난 x, y 좌표를 저장할 필드
    private int x, y;

    // 2.(1) 생성자
    public MyView(Context context) {
        super(context);
    }
    // 2.(2) layout xml 문서에서 해당 View를 사용하게 하려면 필요한 생성자
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 3. onDraw() 메소드 오버라이딩
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        Paint textPaint=new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(100);
        //canvas.drawText("Aloha", 10, 110, textPaint);
        canvas.drawText("x : "+x+" y : "+y, 10, 110, textPaint);
    }

    // 터치 이벤트 처리
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 이벤트가 일어난 곳의 좌표를 필드에 저장 (float(실수) 타입으로 리턴되므로 정수로 캐스팅한다.)
        x=(int)event.getX();
        y=(int)event.getY();

        // MotionEvent(예 : ACTION_DOWN 등)에 대한 상수값이 getAction에 저장되어있다.
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:       // 화면에 손가락을 댔을 때

                break;
            case MotionEvent.ACTION_MOVE:        // 화면에 손가락을 대고 움직일 때 (드래그)

                break;
            case MotionEvent.ACTION_UP:          // 화면에 대고있던 손가락을 떼었을 때

                break;
        }
        // View 갱신하기 (결과적으로 onDraw() 메소드가 다시 호출된다.)
        invalidate();       // View 갱신 (현재 그려진 것(최초 호출된 화면)을 무효화하고 다시 그리기)
        return true;
    }
}
