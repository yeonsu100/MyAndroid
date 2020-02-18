package com.winnie.step04customlist;

import java.io.Serializable;

/*
    Intent 객체에 putExtra()해서 담을 수 있도록
    Serializable 인터페이스를 구현시킨다.
 */
public class CountryDto implements Serializable {
    // 필드
    private int resId;
    private String name;
    private String content;

    // 생성자
    public CountryDto(){}

    // Constructor (마우스 우클릭 > generate > Constructor)
    public CountryDto(int resId, String name, String content) {
        this.resId = resId;
        this.name = name;
        this.content = content;
    }

    // getter and setter

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
