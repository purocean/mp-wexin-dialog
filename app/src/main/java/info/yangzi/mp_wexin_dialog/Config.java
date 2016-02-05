package info.yangzi.mp_wexin_dialog;

/**
 * Created by Yang on 16/1/31.
 */
public class Config {
    public static String apiUrl = "apiUrl";
    public static String token = "Token";
    public static String encodingAESKey = "EncodingAESKey"; // 无效，暂时不支持加密传输模式

    public static String openid = "mp-wexin-dialog"; // 模拟用户的 openid，根据后端程序填写
    public static String devid = "devid"; // 开发者微信号，如果后端程序验证需要填写自己的
}
