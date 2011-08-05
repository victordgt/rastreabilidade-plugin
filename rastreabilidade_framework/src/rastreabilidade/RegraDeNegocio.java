package rastreabilidade;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface RegraDeNegocio {
	
	String identificador();
	

}
