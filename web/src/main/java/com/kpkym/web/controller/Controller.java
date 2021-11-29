package com.kpkym.web.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kpkym.web.compiler.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private String current = IdUtil.simpleUUID();

    /**
     * 拿到上一次生产的随机字符串
     * @return
     */
    @GetMapping("/get")
    public String get() {
        String returnVal = current;

        current = IdUtil.simpleUUID();
        return returnVal;
    }


    @PostMapping("/debug")
    public Object debug(@RequestBody String body) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(body);
        String code = jsonObject.getStr("code");
        String method = jsonObject.getStr("method");

        return Service.start(code, method);
    }
}
