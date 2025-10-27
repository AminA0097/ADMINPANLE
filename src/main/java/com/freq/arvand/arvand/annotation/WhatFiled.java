package com.freq.arvand.arvand.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WhatFiled {
    public enum whatTypes{
        String,ManyToOne,ManyToMany,Date,Boolean,Long;
        public boolean isString() { return this == String; }
        public boolean isLong() { return this == Long; }
        public boolean isBoolean() { return this == Boolean; }
        public boolean isManyToOne() { return this == ManyToOne; }
        public boolean isManyToMany() { return this == ManyToMany; }
    }
    whatTypes type() default whatTypes.String;
}