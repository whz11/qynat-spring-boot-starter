package com.qingyou.qynat.springboot.annotation;

import com.qingyou.qynat.springboot.config.QyNatAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author whz
 * @date 2021/7/24 00:57
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(QyNatAutoConfigure.class)
public @interface EnabledQyNat {
    boolean value() default true;
}
