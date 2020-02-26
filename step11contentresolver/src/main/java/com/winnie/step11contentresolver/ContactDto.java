package com.winnie.step11contentresolver;

public class ContactDto {
    private int id;
    private String phone;
    private String name;

    public ContactDto(){}

    public ContactDto(int id, String phone, String name) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }
}
