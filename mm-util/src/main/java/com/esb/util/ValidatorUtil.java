package com.esb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


/**
 *
 * 登錄驗證工具
 */
public class ValidatorUtil {
	//預設以0開頭後面加10個數字為手機號碼
    private static final Pattern mobile_pattern = Pattern.compile("0\\d{9}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
    
    public static void main(String[] args) {
        System.out.println(isMobile("0938735070"));

    }
}
