package com.example.spring.layui.Inner;

public class Outer {

    private Integer id;

    //成员内部类
    public static class Inner {
        private Integer id;
        private String name;

        public void show() {
            //System.out.println(id);
//            System.out.println(this.id);
//            System.out.println(Outer.this.id);
        }
    }

    public void innerMethod() {
        Integer id = 0;
        class partyInner {
            //int id;

            public void show() {
                //System.out.println(this.id);
                System.out.println(id);
            }
        }
    }
}
