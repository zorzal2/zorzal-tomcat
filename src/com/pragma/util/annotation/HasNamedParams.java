package com.pragma.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Indica que algunos metodos de esta clase usan la anotacion
 * @ParamName. Esto es para no hacer el chequeo sobre cada parametro.
 * @author llobeto
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface HasNamedParams {}
