package info.yangzi.mp_wexin_dialog;

import android.util.Log;

/**
 * Created by Yang on 16/1/31.
 */
public class Msg extends HttpUtility {
    Msg(String msgText) {
        super(
                Config.apiUrl + "&" + Wechat.getParams(Config.token),
                "post",
                Wechat.getSendTextXml(msgText, Config.devid, Config.openid),
                "html/xml"
        );
    }
}
