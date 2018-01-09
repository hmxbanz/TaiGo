package com.xtdar.app.ble;

import android.util.SparseArray;

public class SignTranslator {

    public static final int HEART_BEAT = 0x02403003;
    public static final int HEART_ECHO = 0x02403103;

    public static final int ACK = 0x02430603;
    public static final int NAK = 0x02431503;
    public static final int OnBlowUp = 0x02423303;//score6 42 33 03
    public static final int GetShutCount = 0x02473F03;
    public static final int GetBlowCount = 0x02423F03;
    public static final int GetSmoke = 0x02533F03;
    public static final int GetLife = 0x024C3F03;
    public static final int SetDie = 0x024C3003;
    public static final int SetRebirth = 0x024C3403;

    private static final SparseArray<String> array = new SparseArray<>();

    public static String getSend(int sign) {
        return "发送>>>" + get(sign);
    }

    public static String getRead(int sign) {
        return "读取<<<" + get(sign);
    }
    private static String get(int sign) {
        return array.get(sign, String.format("未知信号 %08X", sign));
    }

    static {
        array.put(ACK, "收到命令正确ACK");
        array.put(NAK, "收到命令错误NAK");

        array.put(0x02503103, "开关机设置: 开机");
        array.put(0x02473003, "激光击中上报: 第0次被击中");
        array.put(0x02473103, "激光击中上报: 第1次被击中");
        array.put(0x02473203, "激光击中上报: 第2次被击中");
        array.put(0x02473303, "激光击中上报: 第3次被击中");
        array.put(0x02423003, "炸弹击中上报: 第0次被击中");
        array.put(0x02423103, "炸弹击中上报: 第1次被击中");
        array.put(0x02423203, "炸弹击中上报: 第2次被击中");
        array.put(0x02423303, "炸弹击中上报: 第3次被击中");
        array.put(0x02533103, "信号弹状态上报: 已喷发(死亡)");
        array.put(0x02533203, "信号弹状态上报: 未喷发(健康)");
        array.put(0x024C3003, "生命状态上报: LED灯全灭(死亡)");
        array.put(0x024C3103, "生命状态上报: 1个LED灯亮");
        array.put(0x024C3203, "生命状态上报: 2个LED灯亮");
        array.put(0x024C3303, "生命状态上报: 3个LED灯亮");

        array.put(OnBlowUp, "APP模拟炸弹击中");
        array.put(GetShutCount, "激光击中查询: APP查询激光击中情况");
        array.put(GetBlowCount, "炸弹击中查询: APP查询炸弹击中情况");
        array.put(GetSmoke, "信号弹状态查询: APP查询烟雾弹状态");
        array.put(GetLife, "生命状态查询: APP查询生命状态");
//        array.put(SetDie, "生命状态设置: 设置阵亡");//与生命上报相同
        array.put(SetRebirth, "生命状态设置: 设置复活");
    }

}
