package com.tankwar;
/*
 * 首先枚举是一个特殊的class
 * 这个class相当于final static 修饰，不能被继承
 * 他的构造方法强制被私有化
 * 所有的枚举都继承自java.lang.Enum类。由于java不支持多继承，所以枚举对象不能再继承其他类
 */
public enum Direction {
	//每个成员变量都是final static 修饰
	UP,LEFT,RIGHT,DOWN
}
