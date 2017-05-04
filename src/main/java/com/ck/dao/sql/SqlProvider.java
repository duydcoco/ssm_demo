package com.ck.dao.sql;

import com.ck.util.StringUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dudycoco on 17-3-15.
 */
public class SqlProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlProvider.class);
    public  String insert(Object bean){
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        StringBuilder insertSql = new StringBuilder();
        List<String> columns = Lists.newArrayList();
        List<String> classFieldNames = Lists.newArrayList();
        Field[] fields = getFields(beanClass);
        insertSql.append("INSERT INTO ").append(tableName).append(" (");
        try{
            for(int x = 0;x<fields.length;x++){
                Field field = fields[x];
                field.setAccessible(true);
                Object object = field.get(bean);
                String fieldName = field.getName();
                if(Objects.isNull(object)){
                    continue;
                }else{
                    columns.add(StringUtils.toUnderlineName(fieldName));
                    classFieldNames.add(field.getName());
                }
            }
        }catch (Exception e){
            throw new RuntimeException("get insert sql catch Exception",e);
        }
        for(int x = 0;x<columns.size();x++){
            if(x==columns.size()-1){
                insertSql.append(columns.get(x));
            }else{
                insertSql.append(columns.get(x)+",");
            }
        }
        insertSql.append(") VALUES (");
        for(int x = 0;x<classFieldNames.size();x++){
            if(x==classFieldNames.size()-1){
                insertSql.append("#{"+classFieldNames.get(x)+"}");
            }else {
                insertSql.append("#{"+classFieldNames.get(x)+"},");
            }
        }
        insertSql.append(")");
        return insertSql.toString();
    }

    public  String update(Object bean){
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        StringBuilder updateSql = new StringBuilder();
        List<String> columns = Lists.newArrayList();
        List<String> classFieldNames = Lists.newArrayList();
        String idField = "";
        Field[] fields = getFields(beanClass);
        updateSql.append("UPDATE ").append(tableName).append(" SET ");
        try{
            for(int x = 0;x<fields.length;x++){
                Field field = fields[x];
                field.setAccessible(true);
                Id id = field.getAnnotation(Id.class);
                Object object = field.get(bean);
                String fieldName = field.getName();
                if(!Objects.isNull(id)){
                    idField = fieldName;
                }
                if(Objects.isNull(object)){
                    continue;
                }else{
                    columns.add(StringUtils.toUnderlineName(fieldName));
                    classFieldNames.add(field.getName());
                }
            }
        }catch (Exception e){
            throw new RuntimeException("get update sql catch Exception",e);
        }
        for(int x = 0;x<columns.size();x++){
            if(x==columns.size()-1){
                updateSql.append(columns.get(x)+"="+"#{"+classFieldNames.get(x)+"}");
            }else{
                updateSql.append(columns.get(x) + "=" + "#{" + classFieldNames.get(x) + "},");
            }
        }
        updateSql.append(" WHERE ").append(StringUtils.toUnderlineName(idField) + "=" + "#{" + idField + "}");
        return updateSql.toString();
    }

//    public String batchInsertInstallmentDetailInfo(Map<String,List<Map>> params){
//        List<Map> datas = params.get("param");
//        Map map = datas.get(0);
//        InstallmentDetailInfo installmentDetailInfo = new InstallmentDetailInfo();
//        Field[] fields = getFields(installmentDetailInfo.getClass());
//        StringBuilder insertSql = new StringBuilder();
//        List<String> keys = Lists.newArrayList();
//        insertSql.append("insert into installmentdetailinfo ( ");
//        for(int x = 0;x< fields.length;x++){
//            Field field = fields[x];
//            String fieldName = field.getName();
//            String colunName = StringUtils.toUnderlineName(fieldName);
//            if(map.containsKey(colunName)){
//                keys.add(colunName);
//                if(x==fields.length-1){
//                    insertSql.append(colunName);
//                }else {
//                    insertSql.append(colunName+",");
//                }
//            }
//        }
//        insertSql.append(") VALUES ");
//        for(int x = 0;x<datas.size();x++){
//            String value = keys.stream().map(item -> "#{list[&]." + item).collect(Collectors.joining(",","(",")"));
//            String str = value.replace("&",x+"");
//            if(x==datas.size()-1){
//                insertSql.append(str);
//            }else {
//                insertSql.append(str+",");
//            }
//        }
//        return insertSql.toString();
//    }

    public static String getTableName(Class<?> clazz){
        String tableName = "";
        Table table = clazz.getAnnotation(Table.class);
        if(!Objects.isNull(table)){
            tableName = table.name();
        }else{
            throw new RuntimeException("the annotation @Table must have a name!!");
        }
        return tableName;
    }

    public static Field[] getFields(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        Class<?> superClass = clazz.getSuperclass();
        Field[] superFields = superClass.getDeclaredFields();
        Field[] allFields = (Field[])ArrayUtils.addAll(fields, superFields);
        return allFields;
    }
}
