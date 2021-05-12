package com.example.demoprotobuf.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DynamicAddParamUtil {
 
    /**
     * 使用表达式，去去掉参数前缀 $cglib_prop_
     */
    private static final Pattern COMPILE = Pattern.compile("^(.*)cglib_prop_(.*)$");
 
/**
     * @param object    旧的对象带值
     * @param addMap    动态需要添加的属性和属性类型
     * @param addValMap 动态需要添加的属性和属性值
     * @return 新的对象
     * @throws
     */
    public static Object dynamicClass(Object object, Map<String, Object> addMap, Map<String, Object> addValMap) {
        HashMap<String, Object> returnMap = new HashMap<>();
        HashMap<String, Object> typeMap = new HashMap<>();
 
        try {
            Class<?> type = object.getClass();
            // 在 Java Bean上进行内省,了解其所有属性、公开的方法和事件
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            // 获得 beans PropertyDescriptor
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                // 获得所有对象属性值得名字
                String propertyName = descriptor.getName();
                if (!"class".equals(propertyName)) {
                    // 设置应用于读取属性值的方法
                    Method readMethod = descriptor.getReadMethod();
                    // 处理代理实例上的方法调用并返回结果
                    Object result = readMethod.invoke(object);
                    returnMap.put(propertyName, result);
                    typeMap.put(propertyName, descriptor.getPropertyType());
                }
            }
 
            returnMap.putAll(addValMap);
            typeMap.putAll(addMap);
            // map转换成实体对象
            DynamicBean bean = new DynamicBean(typeMap);
            // 赋值
            Set keys = typeMap.keySet();
            for (Iterator it = keys.iterator(); it.hasNext(); ) {
                String key = (String) it.next();
                bean.setValue(key, returnMap.get(key));
            }
            return bean.getObject();
        } catch (Exception e) {
            log.warn("动态添加参数错误：{}", e);
            return object;
        }
    }
 
 
    private static class DynamicBean {
        // 动态生成的类
        private Object object = null;
        // 存放属性名称以及属性的类型
        private BeanMap beanMap = null;
 
        public DynamicBean() {
            super();
        }
 
        public DynamicBean(Map propertyMap) {
            this.object = generateBean(propertyMap);
            this.beanMap = BeanMap.create(this.object);
        }
 
        /**
         * 生成Bean
         *
         * @param propertyMap
         * @return
         */
        private Object generateBean(Map propertyMap) {
            BeanGenerator generator = new BeanGenerator();
            Set keySet = propertyMap.keySet();
            for (Iterator i = keySet.iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                generator.addProperty(key, (Class) propertyMap.get(key));
            }
            return generator.create();
        }
 
        /**
         * 给bean属性赋值
         *
         * @param property 属性名
         * @param value    值
         */
        public void setValue(Object property, Object value) {
            beanMap.put(property, value);
        }
 
        /**
         * 通过属性名得到属性值
         *
         * @param property 属性名
         * @return 值
         */
        public Object getValue(String property) {
            return beanMap.get(property);
        }
 
        /**
         * 得到该实体bean对象
         *
         * @return
         */
        public Object getObject() {
            return this.object;
        }
    }
 
    /**
     * 获取对象中的所有属性名与属性值
     *
     * @param object
     * @return
     * @throws
     */
    public static Map<String, Class> getAllPropertyType(Object object) {
        Map<String, Class> map = new HashMap<String, Class>();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (int index = 0; index < fields.length; index++) {
                Field field = fields[index];
                String propertyName = field.getName();
                Class<?> propertyType = Class.forName(field.getGenericType().getTypeName());
                map.put(propertyName, propertyType);
            }
            return map;
        } catch (ClassNotFoundException e) {
            log.error(String.format("从对象%s获取所有属性失败", object));
        }
        return map;
    }
 
    /**
     * 获取对象中的所有属性名与属性值
     *
     * @param object
     * @return
     * @throws
     */
    public static Map<String, Object> getAllPropertyValue(Object object) {
        Map<String, Object> map = new HashMap<>();
        Class<?> aClass = object.getClass();
        if (aClass.equals(HashMap.class) || aClass.equals(JSONObject.class)) {
            return JSONObject.parseObject(JSONObject.toJSONString(object), new TypeReference<Map<String, Object>>(){});
        } else {
            Field[] fields = object.getClass().getDeclaredFields();
            for (int index = 0; index < fields.length; index++) {
                Field field = fields[index];
                String propertyName = field.getName();
                if (propertyName.contains("$cglib_prop_")) {
                    Matcher matcher = COMPILE.matcher(propertyName);
                    if (matcher.find()) {
                        propertyName = matcher.group(2);
                    }
                }
                Object propertyValue = getPropertyValueByName(object, propertyName);
                map.put(propertyName, propertyValue);
            }
            return map;
        }
    }
 
    /**
     * 根据属性名获取对象中的属性值
     *
     * @param propertyName
     * @param object
     * @return
     */
    public static Object getPropertyValueByName(Object object, String propertyName) {
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Object value = null;
        try {
            Method method = object.getClass().getMethod(methodName, new Class[]{});
            value = method.invoke(object, new Object[]{});
        } catch (Exception e) {
            log.error(String.format("从对象%s获取%s的=属性值失败", object, propertyName));
        }
        return value;
    }
}
 
 

 
 
 

