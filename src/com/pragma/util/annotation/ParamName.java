package com.pragma.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Deja como anotacion en un parametro su nombre. Esto es porque
 * Java no guarda el nombre de los parametros en runtime. Se usa
 * para generar en runtime queries con parametros nombrados. Uso:<br>
 * 	public String getSomething(@ParamName("someParameter") String someParameter);<br>
 * La clase ademas debe tener la anotacion @HasNamedParams
 * @author llobeto
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ParamName {
    String value();
}
