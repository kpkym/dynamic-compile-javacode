package com.kpkym.debug.debug;

import cn.hutool.extra.spring.SpringUtil;
import com.kpkym.web.controller.Controller;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Debug {
    public Object invoke() throws Exception {
        Controller bean = SpringUtil.getBean(Controller.class);

        Field[] declaredFields = bean.getClass().getDeclaredFields();
        Field field = Arrays.stream(declaredFields)
                .filter(e -> e.getName().equals("current"))
                .findFirst()
                .get();

        field.setAccessible(true);
        return field.get(bean);
    }
}
