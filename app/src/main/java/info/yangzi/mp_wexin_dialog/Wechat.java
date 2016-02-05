package info.yangzi.mp_wexin_dialog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

/**
 * Created by Yang on 16/2/5.
 */
public class Wechat {
    private WechatMsg _wechatMsg = null;

    Wechat(String xml) {
        this.parseXml(xml);
    }

    public WechatMsg getWechatMsg() {
        return this._wechatMsg;
    }

    public void parseXml(String xml) {
        WechatMsg wechatMsg = new WechatMsg();

        try {
            // 获得pull解析器工厂
            XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();

            //获取XmlPullParser的实例
            XmlPullParser pullParser = pullParserFactory.newPullParser();

            // 设置需要解析的XML数据
            pullParser.setInput(new ByteArrayInputStream(xml.getBytes("UTF-8")), "UTF-8");

            // 取得事件
            int event = pullParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = pullParser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT: // 文档开始
                        break;
                    case XmlPullParser.START_TAG: // 标签开始
                        if ("ToUserName".equals(nodeName)) {
                            wechatMsg.openid = pullParser.nextText();
                        }

                        if ("FromUserName".equals(nodeName)) {
                            wechatMsg.devid = pullParser.nextText();
                        }

                        if ("CreateTime".equals(nodeName)) {
                            wechatMsg.createTime = Integer.valueOf(pullParser.nextText());
                        }

                        if ("MsgType".equals(nodeName)) {
                            wechatMsg.msgType = pullParser.nextText();
                        }

                        if ("Content".equals(nodeName)) {
                            wechatMsg.textContent = pullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG: // 标签结束
                        this._wechatMsg = wechatMsg;
                        break;
                }
                event = pullParser.next(); // 下一个标签
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getParams(String token) {
        String timestamp = "" + (System.currentTimeMillis() / 1000);
        String nonce = Tools.randomString(8);
        String[] tmpArr = {token, timestamp, nonce};

        Arrays.sort(tmpArr);
        String tmpStr = tmpArr[0] + tmpArr[1] + tmpArr[2];
        String signature = Tools.strDigest("SHA1", tmpStr);

        return "signature=" + signature + "&timestamp=" + timestamp + "&nonce=" + nonce;
    }

    public static String getSendTextXml(String msg, String toUser, String fromUser) {
        return " <xml>\n" +
                " <ToUserName><![CDATA[" + toUser + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + fromUser + "]]></FromUserName> \n" +
                " <CreateTime>"+ (System.currentTimeMillis() / 1000) +"</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content><![CDATA["+ msg +"]]></Content>\n" +
                " <MsgId>88888"+ (System.currentTimeMillis() / 1000) +"</MsgId>\n" +
                " </xml>";
    }
}
