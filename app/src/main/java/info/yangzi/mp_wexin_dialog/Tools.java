package info.yangzi.mp_wexin_dialog;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Yang on 16/2/5.
 */
public class Tools {
    /**
     * 产生一个随机的字符串
     * @param length 随机字符串长度
     * @return 生成的随机字符串
     */
    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }

        return buf.toString();
    }

    /**
     * 计算字符串摘要
     * @param algorithm 算法名称，如 md5 sha1
     * @param str 要进行摘要的字符串
     * @return 字符串
     */
    public static String strDigest(String algorithm, String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 byte 转换为 String
     *
     * @param buffer 字节
     * @return 字符串
     */
    private static String byte2Hex(byte buffer[]) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }

        return sb.toString();
    }
}
