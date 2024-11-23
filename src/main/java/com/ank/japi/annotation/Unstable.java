package com.ank.japi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, FIELD, METHOD, ANNOTATION_TYPE, CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Unstable {
}
