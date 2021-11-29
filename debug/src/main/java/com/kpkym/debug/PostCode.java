package com.kpkym.debug;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.boot.system.ApplicationHome;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PostCode {
    public static String java = "Debug.java";
    public static String method = "invoke";

    public static void main(String[] args) throws Exception {

        // 拿到当前class路径
        List<String> split = StrUtil.split(new ApplicationHome(PostCode.class).getDir().getAbsolutePath(), StrUtil.C_SLASH);
        // 项目文件夹名
        String projectDirName = "dynamic-compile-javacode";

        while (CollUtil.isNotEmpty(split)) {
            if (CollUtil.getLast(split).equals(projectDirName)) {
                break;
            }
            split.remove(split.size() - 1);
        }


        String debugCodeFile = CollUtil.join(split, StrUtil.SLASH)
                + "/debug/src/main/java/" + StrUtil.replace(PostCode.class.getPackage().getName(), ".", StrUtil.SLASH)
                + "/debug/"
                + java;


        String code = FileUtil.readString(debugCodeFile, UTF_8);

        JSONObject postJson = JSONUtil.createObj()
                .set("code", code)
                .set("method", method);

        // 发送javacode 在服务器编译后返回结果给本地
        String post = HttpUtil.post("http://localhost:5678/debug", postJson.toString());
        String get = HttpUtil.get("http://localhost:5678/get");

        System.out.println("下一个随机字符串是: " + post);
        System.out.println("get 随机字符串是: " + get);
    }
}
