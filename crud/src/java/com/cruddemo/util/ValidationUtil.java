/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.util;

import com.cruddemo.dao.UserDao;
import com.cruddemo.model.User;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
//import sun.security.jca.GetInstance;

/**
 *
 * @author nitin
 */
public class ValidationUtil {

    public static String empty_msg = "{fieldname} should not be empty";
    public static String max_length_msg = "{fieldname} should not be greter than {size}";
    public static String email_msg = "{email} invalid mail";
    public static String exists_value_msg = "{fieldName} already exists.";
    public static HashMap data = new HashMap();
    public HashMap<String, String> errorMsg = new HashMap<>();
    public static HashMap<String, String> isvalid = new HashMap<>();
    public static String fieldName = "";
    public static String fieldValue = "";
    public static ValidationUtil validationObj = null;

    public static String getFieldName() {
        return fieldName;
    }

    public static ValidationUtil getInstance() {
        if (validationObj == null) {
            validationObj = new ValidationUtil();
        }
        return validationObj;
    }

    public static void setFieldName(String fieldName) {
        ValidationUtil.fieldName = fieldName;
    }

    public static String getFieldValue() {
        return fieldValue;
    }

    public static void setFieldValue(String fieldValue) {
        ValidationUtil.fieldValue = fieldValue;
    }

    public static HashMap<Integer, Field> getFields(Class<? extends Object> dataClass) {
        HashMap<Integer, Field> fields = new HashMap<>();
        int i = 0;

        for (Field field : dataClass.getDeclaredFields()) {

            fields.put(i, field);
            i++;
        }
        return fields;
    }

    public static void setValidationMsg(String fieldName, String msg, String rule) {

        System.out.println("isvalid hashmap fsdf: " + isvalid);
        //HashMap inner = new HashMap();
        // inner.put(rule, msg);

        // errorMsg.put(fieldName,inner);
        System.out.println("fieldnamefdsgs: " + fieldName + " msg: " + msg);

//        if(!ValidationUtil.getInstance().errorMsg.containsKey(fieldName)) {
//                ValidationUtil.getInstance().errorMsg.put(fieldName,msg);
//            }
//            else {
//             ValidationUtil.getInstance().errorMsg.put(fieldName, (ValidationUtil.getInstance().errorMsg.get(fieldName)+1);
//            }
//      
        ValidationUtil.getInstance().errorMsg.put(fieldName, msg);
//        if(isvalid.isEmpty())
//        {
//            System.out.println("empty is valid");
//            ValidationUtil.getInstance().errorMsg.clear();
//        }
//        else
//        {
//             System.out.println("empty not is valid");
//            for (Object name : isvalid.keySet()) {
//
//              
//                String key = name.toString();
//                String value = isvalid.get(name).toString();
//                if(value.equalsIgnoreCase("true"))
//                {
//                    ValidationUtil.getInstance().errorMsg.remove(key);
//                    
//                    System.out.println("keyis............: "+key+ " value is: "+value);
//                }
//                
//                
//        }
//        }

//        System.out.println("error field: " + fieldName);
//        System.out.println("error msg: " + msg);
//        System.out.println("error rule: " + rule);
    }

    public static void validateRules(HashMap validatedata) {
        
        System.out.println("here is validate data: "+validatedata);
        if (validatedata != null) {
          //  System.out.println("hello");

            if (validatedata.get(constantUtil.fieldName.toString()) != null) {

                if (validatedata.containsKey(constantUtil.fieldName.toString())) {

                    setFieldName(validatedata.get(constantUtil.fieldName.toString()).toString());
                    validatedata.remove(constantUtil.fieldName.toString());
                }
            }

            if (validatedata.get(constantUtil.fieldValue.toString()) != null) {

                if (validatedata.containsKey(constantUtil.fieldValue.toString())) {

                    setFieldValue(validatedata.get(constantUtil.fieldValue.toString()).toString());
                    validatedata.remove(constantUtil.fieldValue.toString());
                }
            }

            System.out.println("isvalid: "+isvalid);
            if (!isvalid.isEmpty()) {
                for (Object name : isvalid.keySet()) {

                    String key = name.toString();
                    String value = isvalid.get(name).toString();
                    if (value.equalsIgnoreCase("true")) {
                        ValidationUtil.getInstance().errorMsg.remove(key);

                       // System.out.println("keyis............: " + key + " value is: " + value);
                    }

                }
            }
            //int i = 1;
            for (Object name : validatedata.keySet()) {

                String key = name.toString();
                String value = validatedata.get(name).toString();

                //System.out.println("key is: " + key);
                // System.out.println("inside loop .....: "+i+" "+key+" values..."+value);
                //i++;
                if (key.equalsIgnoreCase(constantUtil.notEmpty.toString())) {
                   // System.out.println("getfieldvalue: " + getFieldValue());
                     System.out.println("fieldValue in empty: "+getFieldValue());
                    boolean isempty = isEmptyFieldValue(getFieldValue());
                    System.out.println("isempty:  "+isempty);
                    if (isempty) {
                        isvalid.put(getFieldName(), "false");
                        setValidationMsg(getFieldName(), empty_msg.replace("{fieldname}", getFieldName()), constantUtil.notEmpty.toString());
                    } else {
                        isvalid.put(getFieldName(), "true");
                        // ValidationUtil.getInstance().errorMsg.remove(getFieldName());
                    }

                   // System.out.println("errormsg:1 " + ValidationUtil.getInstance().errorMsg);

                } else if (key.equalsIgnoreCase(constantUtil.maxLength.toString())) {
                    // System.out.println("get value is :"+getFieldValue());
                    boolean ismaxLength = maxLength(getFieldValue(), Integer.valueOf(value));
                    if (ismaxLength) {
                        isvalid.put(getFieldName(), "false");
                        //  System.out.println("max length");
                        String maxmsg = max_length_msg.replace("{fieldname}", getFieldName());
                        maxmsg = maxmsg.replace("{size}", value);
                        setValidationMsg(getFieldName(), maxmsg, constantUtil.maxLength.toString());

                    } else {
                        isvalid.put(getFieldName(), "true");
                        // ValidationUtil.getInstance().errorMsg.remove(getFieldName());
                    }
                } else if (key.equalsIgnoreCase(constantUtil.isValidEmail.toString())) {

                   // System.out.println("errormsg:2 " + ValidationUtil.getInstance().errorMsg);
                   // System.out.println("get value is :" + getFieldValue());

                    boolean isValidEmail = isValidEmail(getFieldValue());
                    System.out.println("is valid email: " + isValidEmail);

                    if (!isValidEmail) {
                        isvalid.put(getFieldName(), "false");
                       // ValidationUtil.getInstance().errorMsg.remove("email");
                        // ValidationUtil.getInstance().errorMsg.put("email2","nitin");
                        //  System.out.println("max length");
                        String emailmsg = email_msg.replace("{email}", getFieldValue());
                        // maxmsg = maxmsg.replace("{size}", value);
                        setValidationMsg(getFieldName(), emailmsg, constantUtil.isValidEmail.toString());

                    } else {
                        isvalid.put(getFieldName(), "true");
                        // ValidationUtil.getInstance().errorMsg.remove(getFieldName());
                    }
                } else if (key.equalsIgnoreCase(constantUtil.isExistsEmail.toString())) {
                  //  System.out.println("here is is exists validation");
                     //System.out.println("errormsg:2 "+ValidationUtil.getInstance().errorMsg);
                    //System.out.println("get value is :"+getFieldValue());

                    boolean isExistsEmail = isExistsEmail(getFieldValue());
                   // System.out.println("is isExistsEmail email: " + isExistsEmail);

                    if (isExistsEmail) {

                        isvalid.put(getFieldName(), "false");

                        String emailmsg = exists_value_msg.replace("{fieldName}", getFieldName());
                        setValidationMsg(getFieldName(), emailmsg, constantUtil.isExistsEmail.toString());

                    } else {
                        isvalid.put(getFieldName(), "true");
                        // ValidationUtil.getInstance().errorMsg.remove(getFieldName());
                    }
                }

                // System.out.println(".................................\n");
                // System.out.println(key + ":" + value);
                // System.out.println(".................................\n");
            }
            
//              if (!isvalid.isEmpty()) {
//                for (Object name : isvalid.keySet()) {
//
//                    String key = name.toString();
//                    String value = isvalid.get(name).toString();
//                    if (value.equalsIgnoreCase("true")) {
//                        ValidationUtil.getInstance().errorMsg.remove(key);
//
//                        //System.out.println("keyis............: " + key + " value is: " + value);
//                    }
//
//                }
//            }

        }

    }

    public static boolean maxLength(String value, int max) {
        //System.out.println("length is: "+value.length());
        // System.out.println("max size: "+max);
        if (value.length() <= max) {
            return false;
        } else {
            return true;
        }
        //return (value.length() <= max);
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public static boolean isExistsEmail(String email) {
        System.out.println("validation email is: " + email);
        return new UserDao().isExistsEmail(email);
    }

    public static boolean minLength(String value, int min) {

        if (value.length() >= min) {
            return true;
        } else {
            return false;
        }
//        return (value.length() >= min);
    }

    public static boolean processFields(Object classobj) {
        try {

            HashMap<Integer, Field> fields = getFields(classobj.getClass());

            fields.remove(0);
            //System.out.println("fields: "+fields.toString());
            for (int i = 1; i <= fields.size(); ++i) {

                //System.out.println("fields: "+fields.toString());
                Field field = fields.get(i);
                field.setAccessible(true);

                if (field == null) {
                    continue;
                }
                //System.out.println("field name:"+field.getName());
                // Field f = classobj.getClass().getField(field.getName().toString());
                //System.out.println("value is: "+f.get(classobj.getClass()));

                if (field.get(classobj) == null) {
                    data.put(field.getName(), "");
                    continue;
                }

                // System.out.println("value is: "+field.get(classobj).toString());
                data.put(field.getName(), field.get(classobj).toString());
//                System.out.println("field name:"+field.getName());
//                System.out.println("field value:"+field.toString());
            }

            System.out.println("...........................");
            for (Object name : data.keySet()) {

                String key = name.toString();
                String value = data.get(name).toString();
                System.out.println(key + ":" + value);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void validateData(Object classobj) {
        processFields(classobj);

    }

    public static boolean isEmptyFieldValue(String value) {
        if (value == null) {
            return true;
        } else if (value.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

//    public static void main(String[] args) {
//
//        User u = new User();
//        u.setFirstName("nitin");
//        u.setLastName("ranmare");
//        u.setEmail("");
//        u.setDob(null);
//
//        HashMap<String, String> hm = new HashMap<>();
//        
//        hm.put("fieldName", "firstname");
//        hm.put("fieldValue", "nitin");
//        hm.put("notEmpty", "firstname");
//        hm.put("maxLength", "5");
//
//        validateRules(hm);
//        
//        
////        HashMap<String, String> hm2 = new HashMap<>();
////        hm2.put("fieldName", "lastname");
////        hm2.put("fieldValue", "");
////        hm2.put("notEmpty", "lastname");
////        hm2.put("maxLength", "3");
////
////        validateRules(hm2);
//        System.out.println(errorMsg);
//        //System.out.println(errorMsg.get("firstname"));
//        
//            //String value = ((HashMap<String, String>)errorMsg.get("firstname")).get(constantUtil.maxLength.toString()).toString();
//        //Map<HashMap<String, String>> map = errorMsg.get("firstname");
//         //Value value = map.get(constantUtil.maxLength);
//        //List l1 = new ArrayList();
//        //l1.add("notEmpty");
//
//           // System.out.println("value si:"+value);
//        //processFields(u);
//    }
}
