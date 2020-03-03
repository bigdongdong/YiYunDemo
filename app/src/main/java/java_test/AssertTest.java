package java_test;

import android.annotation.SuppressLint;

public class AssertTest {
    @SuppressLint("Assert")
    public static void main(String[] args) {

        Integer i = null;

        System.out.println("1");
//        try{
//            assert i != null;
//            System.out.println(i.byteValue());
//            System.out.println("2");
//            System.out.println("2");
//            System.out.println("2");
//        }catch (Exception e){}

        assert i != null;
        System.out.println(i.byteValue());
        System.out.println("2");
        System.out.println("2");
        System.out.println("2");


        System.out.println("1");
    }
}
