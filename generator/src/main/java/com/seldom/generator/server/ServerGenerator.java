package com.seldom.generator.server;

import com.seldom.generator.util.DbUtil;
import com.seldom.generator.util.Field;
import com.seldom.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.*;

public class ServerGenerator {
    //    static String toPath = "generator/src/main/java/com/seldom/generator/test/";
    static String MODULE = "business";
    static String toDtoPath = "server/src/main/java/com/seldom/server/dto/";
    static String toServicePath = "server/src/main/java/com/seldom/server/service/";
    static String toControllerPath = MODULE + "/src/main/java/com/seldom/" + MODULE + "/controller/admin/";

    public static void main(String[] args) throws Exception {
        String Domain = "Section";
        String domain = "section";
        String tableName = "Section";
        String module = MODULE;

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableName", tableName);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        // 生成dto
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);

        // 生成 service
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);

        // 生成 controller
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);
    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}