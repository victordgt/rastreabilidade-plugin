package rastreabilidade.plugin.anotacao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnotacaoUtil {
	
	public static boolean temAnotacaoClasse(Class<?> clazz, Class<?> artefato) {
		for (Annotation anotacao : artefato.getAnnotations()) {
			if (comparaAnotacaoClasse(clazz, anotacao)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean temAnotacaoMetodo(Class<?> clazz, Class<?> artefato) {
		for (Method metodos : artefato.getMethods()) {
			for (Annotation anotacao :metodos.getAnnotations()) {
				if (comparaAnotacaoClasse(clazz, anotacao)) {
					return true;
				}
			}		
		}
		return false;
	}
	
	public static Annotation getAnotacaoClasse(Class<?> clazz, Class<?> artefato) {
		for (Annotation anotacao : artefato.getAnnotations()) {
			if (comparaAnotacaoClasse(clazz, anotacao)) {
				return anotacao;
			}
		}
		
		throw new IllegalArgumentException("Anotacao Inexistente");
	}
	
	public static List<Method> getMetodoComAnotacao(Class<?> clazz, Class<?> artefato) {
		List<Method> metodos = new ArrayList<Method>();
		for (Method metodo : artefato.getMethods()) {
			for (Annotation anotacao :metodo.getDeclaredAnnotations()) {
				if (comparaAnotacaoClasse(clazz, anotacao)) {
					metodos.add(metodo);
				}
			}		
		}
		
		throw new IllegalArgumentException("Anotacao Inexistente");
	}

	private static boolean comparaAnotacaoClasse(Class<?> clazz,
			Annotation anotacao) {
		return anotacao.annotationType().getName().equals(clazz.getName());
	}
	

}