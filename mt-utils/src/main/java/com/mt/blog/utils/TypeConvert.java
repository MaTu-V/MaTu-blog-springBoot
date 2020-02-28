package com.mt.blog.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TypeConvert {
    // object转map
    public static Map<String, Object> getObjectToMap(Object obj){
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
            }
            map.put(fieldName, value); // 允许压入空
        }
        return map;
    }
    // Map转Object
    public static Object mapToObject(Map<Object, Object> map, Class<?> beanClass)  {
        if (map == null)
            return null;
        Object obj = null;
        try {
            obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                if (map.containsKey(field.getName())) {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return obj;
    }
    // 时间日期转换
    public static String DateToString(Date date){
        SimpleDateFormat temp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //这是24时
        return temp.format(new Date());
    }
}
