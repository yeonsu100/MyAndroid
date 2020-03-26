package com.winnie.step20hellokotlin

// 인자로 Int type 2개를 전달받아서 Int type을 리턴해주는 함수
fun sum(a: Int, b: Int): Int{
    // return이라는 예약어로 값을 리턴해준다
    return a+b
}

// return type이 Int라는 것을 암시적으로 알려준다
fun sum2(a: Int, b: Int)=a+b

fun main(){
    println(sum(10, 20))
    println(sum2(5, 5))

    // read only 지역변수를 만들 때 사용하는 예약어 val
    val a: Int=10
    // a=20 값 수정 불가
    val b=20        // type이 추론되는 경우 type 생략 가능
    val c: Int      // 선언만 하고 값을 나중에 대입할 때는 type을 선언해야 한다
    c=30

    var myNick="Winnie"
    var myName="My name is $myNick"
    myNick="Winnieee"

    var result="${myName.replace("is", "was")}" +
            " but now is $myNick"

    println(result)

    val items = listOf("apple", "banana", "kiwifruit")
    // items.indices는 배열의 인덱스만 모아놓은 배열이다
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

}