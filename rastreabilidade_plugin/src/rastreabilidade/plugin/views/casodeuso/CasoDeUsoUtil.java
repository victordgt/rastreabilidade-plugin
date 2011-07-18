package rastreabilidade.plugin.views.casodeuso;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import rastreabilidade.CasoDeUso;

public class CasoDeUsoUtil {
	public static boolean temAnotacaoCasoDeUso(Class<?> clazz) {
		for (Annotation anotacao : clazz.getAnnotations()) {
			
	
			if (anotacao.annotationType().getName().equals(CasoDeUso.class.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean temAnotacaoMetodoCasoDeUso(Class<?> clazz) {
		for (Method metodos : clazz.getMethods()) {
			for (Annotation anotacao :metodos.getAnnotations()) {
				if (anotacao instanceof CasoDeUso) {
					return true;
				}
			}		
		}
		return false;
	}
	
	public static Annotation getAnotacaoClasse(Class<?> clazz) {
		for (Annotation anotacao : clazz.getAnnotations()) {
			if (anotacao.annotationType().getName().equals(CasoDeUso.class.getName())) {
				
				return anotacao;
			}
		}
		
		throw new IllegalArgumentException("Anotacao Inexistente");
	}
	
	public static List<Method> getMetodoComAnotacao(Class<?> clazz) {
		List<Method> metodos = new ArrayList<Method>();
		for (Method metodo : clazz.getMethods()) {
			for (Annotation anotacao :metodo.getDeclaredAnnotations()) {
				if (anotacao instanceof CasoDeUso) {
					metodos.add(metodo);
				}
			}		
		}
		
		throw new IllegalArgumentException("Anotacao Inexistente");
	}
	
	public static CasoDeUso getAnotacaoMetodo(Method metodo) {
		for (Annotation anotacao : metodo.getAnnotations()) {
			if (anotacao instanceof CasoDeUso) {
				return (CasoDeUso)anotacao;
			}
		}
		
		throw new IllegalArgumentException("Anotacao Inexistente");
	}

}