package com.winnie.drawpanel;

import java.io.Serializable;

public class Point implements Serializable {    // 객체를 파일에 저장, 읽어들이기 위해 serializable한다.
    public int x;
    public int y;
    public boolean isStartPoint;
}
