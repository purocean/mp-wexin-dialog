package info.yangzi.mp_wexin_dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

public class HttpUtility extends AsyncTask<String,Integer,Boolean> {
    private String _logTag = "HttpUtility";
    protected Boolean debug = false;

    private String _url = "";
    private String _method = "get";
    private String _params = "";
    private String _response = "";
    private String _error = "ok";
    private String _postContentType = "application/x-www-form-urlencoded";

    HttpUtility(String url, String method, String params, String postContentType) {
        this._url = url;
        this._method = method;
        this._params = params;
        this._postContentType = postContentType;
    }

    HttpUtility(String url, String method, String params) {
        this._url = url;
        this._method = method;
        this._params = params;
    }

    HttpUtility(String url, String method) {
        this._url = url;
        this._method = method;
    }

    HttpUtility(String url) {
        this._url = url;
    }

    /**
     * 控制调试开关
     * @param debug 是否开启调试
     * @return 这个实例
     */
    public HttpUtility debug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    /* Override */
    /**
     * 发送之前回调
     */
    public void preSend() {
    }

    /**
     * 成功回调
     * @param result HTTP 响应
     */
    public void done(String result) {
    }

    /**
     * 错误回调
     */
    public void fail(String error) {
    }

    /**
     * TODO 进度回调
     * @param progress 0 到 100
     */
    public void progress(Integer progress) {
    }

    /**
     * 总是回调
     */
    public void always() {
    }

    /* AsyncTask override */

    @Override
    protected void onPreExecute() {
        this.preSend();
    }

    @Override
    protected Boolean doInBackground(String ... params ) {
        //        super.publishProgress(i);
        if (this._isGet() || this._isPost()) {
            this._response = this._send();
        } else {
            this._error = "methodError";
        }

        return Boolean.TRUE;
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        this.progress(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if ("ok".equals(this._error)) {
            this.done(this._response);
        } else {
            this.fail(this._error);
        }

        this.always();

        super.onPostExecute(result);
    }

    private String _send() {
        String url = this._url;
        String params = this._params;

        String result = "";
        BufferedReader bufferedReader = null;

        try {
            URL realUrl = new URL(url);
            try {
                URLConnection urlConnection = realUrl.openConnection();

                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("accept", "*/*");
                urlConnection.setRequestProperty("connection", "Keep-Alive");
                urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.14)");

                if (this._isPost()) {
                    urlConnection.setRequestProperty("Content-Type", this._postContentType);

                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);

                    //获取 URLConnection 对象对应的输出流
                    PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
                    //发送请求参数
                    printWriter.print(params);
                    //flush 输出流缓冲
                    printWriter.flush();
                } else {
                    urlConnection.connect();
                }

                if (this.debug) {
                    // 获取所有响应头字段
                    Map<String, List<String>> map = urlConnection.getHeaderFields();

                    // 遍历所有响应头字段
                    for (String key:map.keySet()){
                        this._log("" + map.get(key));
                    }
                }

                // 定义 BufferReader 输入流来读取URL的响应
                // TODO 获取二进制流
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                for (; (line = bufferedReader.readLine()) != null; ){
                    result += line + "\n";
                }
            } catch (IOException e) {
                this._error = "netError";
                this._log("发送请求异常" + e);
            }
        } catch (MalformedURLException e) {
            this._error = "urlError";
            this._log("Url 异常" + e);

            e.printStackTrace();
        } finally {
            if (null != bufferedReader){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.debug) {
            this._log(result);
        }

        return result;
    }

    private Boolean _isPost() {
        return "post".equals(this._method);
    }

    private Boolean _isGet() {
        return "get".equals(this._method);
    }

    private void _log(String log) {
        if (this.debug) {
            Log.i(this._logTag + "/" + this._method, log);
        }
    }
}