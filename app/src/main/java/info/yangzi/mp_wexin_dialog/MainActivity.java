package info.yangzi.mp_wexin_dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMsg(View button) {
        EditText msgEditText = (EditText) findViewById(R.id.msgText);
        final EditText logEdtText = (EditText) findViewById(R.id.logText);

        String msgText = msgEditText.getText().toString().trim();

        if (msgText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入消息内容", Toast.LENGTH_SHORT).show();
        } else {
            new Msg(msgText){
                @Override
                public void preSend() {
                    Toast.makeText(getApplicationContext(), "发送中……", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void done(String resut) {
                    Wechat wechat = new Wechat(resut);
                    logEdtText.setText(wechat.getWechatMsg().textContent);
                    Toast.makeText(getApplicationContext(), wechat.getWechatMsg().msgType, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void fail(String error) {
                    Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void always() {
                    Toast.makeText(getApplicationContext(), "发送完成", Toast.LENGTH_SHORT).show();
                }
            }.debug(true).execute();
        }
    }
}
