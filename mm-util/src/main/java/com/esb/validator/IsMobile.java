package com.esb.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
* 自定義手機格式驗證注解
*/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
       validatedBy = {IsMobileValidator.class}
)//引入驗證器
public @interface IsMobile {

   boolean required() default true;//預設不能為空

   String message() default "手機號碼格式錯誤";//驗證不通過輸出訊息

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}