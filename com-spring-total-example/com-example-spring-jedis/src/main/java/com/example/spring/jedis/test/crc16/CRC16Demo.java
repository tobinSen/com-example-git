package com.example.spring.jedis.test.crc16;

public class CRC16Demo {


    public static void main(String[] args) {
        System.out.println(Integer.parseInt(getCRC("tong".getBytes())) % 16383);
        System.out.println(Integer.parseInt(getCRC("jian".getBytes())) % 16383);
        System.out.println(Integer.parseInt(getCRC("tongJian".getBytes())) % 16383);

    }

    /**
     * 计算CRC16校验码
     *
     * @param bytes
     * @return
     */
    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }
}
