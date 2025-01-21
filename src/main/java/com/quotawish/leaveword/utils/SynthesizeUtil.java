package com.quotawish.leaveword.utils;

import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class SynthesizeUtil {

    static final String END_URL = "http://tts.tagzxia.com/v1/audio/speech";
    static final String API_KEY = "DEo1g79CuJVrunSmUhUmPhMHnRr5iZWd";

    /**
     * 发送 POST 请求，并且返回音频文件流
     */
    public static byte[] synthesize(String voice, String input) {
        return HttpUtil.createPost(END_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .body(
                        JSONUtil.createObj()
                                .putOnce("voice", voice)
                                .putOnce("input", input)
                                .putOnce("model", "tts-1")
                                .toJSONString(2)
                )
                .execute()
                .bodyBytes();
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL(END_URL);

        url.openConnection().connect();



        byte[] bytes = SynthesizeUtil.synthesize("zh-CN-XiaoxiaoNeural", "你好");

        // 保存到桌面
        try {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\TalexDS\\Downloads\\test.mp3");
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
