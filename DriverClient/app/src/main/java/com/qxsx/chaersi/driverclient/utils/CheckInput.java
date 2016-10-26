package com.qxsx.chaersi.driverclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chaersi on 16/8/5.
 */
public class CheckInput {


    /**
     * 验证是否是手机号码
     *
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone) {
        Pattern phonePattern = Pattern.compile("^1[3|4|5|8|7][0-9]\\d{8}$");
        Matcher matcher = phonePattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 验证是否符合密码
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{8,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是分组名
     * @param s
     * @return
     */
    public static boolean isGroupName(String s){
        Pattern phonePattern = Pattern.compile("[a-zA-Z0-9\\u4E00-\\u9FA5]{1,4}");
        Matcher matcher = phonePattern.matcher(s);
        return matcher.matches();

    }

    /**
     * 判断是否是采暖用户姓名
     * @param s
     * @return
     */
    public static boolean isHeaterName(String s){
        Pattern phonePattern = Pattern.compile("[\\s\\u4E00-\\u9FA5]{1,50}");
        Matcher matcher = phonePattern.matcher(s);
        return matcher.matches();

    }
}
