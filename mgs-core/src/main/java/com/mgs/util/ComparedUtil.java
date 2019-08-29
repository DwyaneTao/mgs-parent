package com.mgs.util;

import com.mgs.annotations.Compared;
import org.apache.commons.collections.CollectionUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ComparedUtil {

    // o1为修改前的对象，o2为修改后的对象
    public static String getComparedContent(Object object1, Object object2) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        Class clazz = object1.getClass();
        Class cluzz = object2.getClass();

        if(clazz == cluzz) {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
                    Object.class).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                //获取get方法
                Method readMethod = pd.getReadMethod();
                Object o1 = readMethod.invoke(object1);
                Object o2 = readMethod.invoke(object2);

                Field field = clazz.getDeclaredField(pd.getName());
                //获取注解的值
                Compared compared = field.getAnnotation(Compared.class);

                if (compared == null) {
                    continue;
                }

                if ((o1 == null && o1 != o2) || (o2 == null && o1 != o2) || (o1 != null && o2 != null && !o1.equals(o2))) {
                    //若是没有分割符号或者分割符为|就默认用修改语句 （这是为了酒店早餐的独特处理）
                    // 建议若是用比较的话，重新写个方法！
                    //属于单选的修改内容逻辑
                    if (compared.split().equals("") || compared.split().equals("\\|")) {
                        stringBuffer.append("将\"" + compared.variableName() + "\"修改为" + (null == o2 ? "空" : handlerOneChoice(compared, o2)) + ";");
                    } else { //包含多种类型的String
                        stringBuffer.append("将\"" + compared.variableName() + "\""+ (null == o2 ? "修改为空" : handlerManyChoice(compared, o1, o2)) + ";");
                    }
                }

            }
        }
        return stringBuffer.toString();
    }

    /**
     * 处理变化内容
     * @param enumsName
     * @param stringBuffer
     */
    private static void handlerEnumsContents(String enumsName, StringBuffer stringBuffer, Object modifyContent) throws Exception{
        //若是没有枚举值的话，则返回它的内容
        if(!StringUtil.isValidString(enumsName)){
            stringBuffer.append(modifyContent);
            return ;
        }

        Class aClass = Class.forName(enumsName);
        Object[] arr = aClass.getEnumConstants();
        for(Object object: arr){
           Class<?> clzz = object.getClass();
           Method method = clzz.getDeclaredMethod("getNo");
           Integer no = (Integer) method.invoke(object);
           if(String.valueOf(no).equals(String.valueOf(modifyContent))){
               Method mtd = clzz.getDeclaredMethod("getDesc");
               String desc = (String) mtd.invoke(object);
               stringBuffer.append(desc);
               break;
           }
        }
    }

    /**
     * 处理只有一个选项的值
     * @return
     */
    private static String handlerOneChoice(Compared compared, Object o2) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        if(!compared.split().equals("")){//处理有分割符的逻辑
            String[] lists = ((String) o2).split(compared.split());
            for(int i = 0; i < lists.length; i ++) {
                if(i == 0){
                    handlerEnumsContents(compared.enumsName(), stringBuffer, lists[i]);
                    continue;
                }
                stringBuffer.append("|"+ lists[i]);
            }
        }else {// 处理结果有枚举的话
            handlerEnumsContents(compared.enumsName(), stringBuffer, o2);
        }
        return stringBuffer.toString();
    }


    /**
     * 处理有多选的变量
     * @return
     */
    private static String handlerManyChoice(Compared compared, Object o1, Object o2) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();

        //修改前的List
        List<String> beforeList = Arrays.asList(((String) o1).split(compared.split()));
        //修改后的List
        List<String> nowList = new ArrayList<String>(Arrays.asList(((String) o2).split(compared.split())));

        //标志是否有修改的数据
        int flag = 0;


        if(CollectionUtils.isNotEmpty(beforeList)){
            for(String before: beforeList){
                //若是修改后的List没有修改前的数据，即为删除
                if(nowList.contains(before)){
                    nowList.remove(before);
                    continue;
                }
                //删除
                if(flag == 0){
                    stringBuffer.append("删除");
                    flag = 1;
                }
                handlerEnumsContents(compared.enumsName(), stringBuffer, before);
                stringBuffer.append(",");
            }

            if(CollectionUtils.isNotEmpty(nowList)){
                if(flag == 1) {
                    stringBuffer.append("增加");
                }

                for(String add: nowList){
                    handlerEnumsContents(compared.enumsName(), stringBuffer, add);
                }
            }
        }
        return stringBuffer.toString();
    }
}
