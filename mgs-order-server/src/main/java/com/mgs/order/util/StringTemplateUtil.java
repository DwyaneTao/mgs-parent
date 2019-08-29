package com.mgs.order.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.StringWriter;
import java.util.Map;

public class StringTemplateUtil {

    public static Configuration cfg;
    static {
        cfg = new Configuration(new Version("2.3.28"));
    }

    /**
     * Freemarker渲染模板
     * @param template 模版
     * @param params   参数
     * @return
     */
    public static String processFreemarker(String template, Map<String, Object> params) {
        if (template == null || params == null)
            return null;
        try {
            StringWriter result = new StringWriter();
            Template tpl = new Template("strTpl", template, cfg);
            tpl.process(params, result);
            return result.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
