package com.ruoyi.web.controller.demo.controller;

class Parent {
    static {
        System.out.println("父类的静态块");
    }
    private static String staticStr = getStaticStr();
    private String str = getStr();
    {
        System.out.println("父类的实例块");
    }
    public Parent() {
        System.out.println("父类的构造方法");
    }
    private static String getStaticStr() {
        System.out.println("父类的静态属性初始化");
        return null;
    }
    private String getStr() {
        System.out.println("父类的实例属性初始化");
        return null;
    }
}

class Child extends Parent{
    private static String staticStr = getStaticStr();
    static {
        System.out.println("子类的静态块");
    }
    {
        System.out.println("子类的实例块");
    }
    public Child() {
        System.out.println("子类的构造方法");
    }
    private String str = getStr();
    private static String getStaticStr() {
        System.out.println("子类的静态属性初始化");
        return null;
    }
    private String getStr() {
        System.out.println("子类的实例属性初始化");
        return null;
    }
}

class Test {
    public static void main(String[] args) {
        new Child();
    }
}