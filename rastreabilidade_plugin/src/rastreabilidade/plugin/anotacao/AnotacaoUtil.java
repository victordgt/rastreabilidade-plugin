package rastreabilidade.plugin.anotacao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class AnotacaoUtil {
	
	public static boolean temAnotacaoClasse(Class<?> clazz, Class<?> artefato) {
		for (Annotation anotacao : artefato.getAnnotations()) {
			if (comparaAnotacaoClasse(clazz, anotacao)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean temAnotacaoClasse(Class<?> clazz, IType artefato) {
		try {
			for (IAnnotation anotacao : artefato.getAnnotations()) {
				if (comparaAnotacaoClasse(clazz, anotacao)) {
					return true;
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
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
	
	public static Annotation getAnotacaoClasse(Class<? extends Annotation> clazz, Class<?> artefato) {
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
	
	public static boolean comparaAnotacaoClasse(Class<?> clazz,
			IAnnotation anotacao) {
		return clazz.getName().indexOf(anotacao.getElementName()) >= 0;
	}	
	
	
	public Object recuperaMemberValuePair(String chave, IMemberValuePair[] memberValuePairArray) {
		if (memberValuePairArray == null) {
			throw new IllegalArgumentException("N�o existem valores para a anota��o");
		}
		for (IMemberValuePair memberValuePair: memberValuePairArray) {
			if (memberValuePair.getMemberName().equals(chave)) {
				return memberValuePair.getValue();
			}	
		}
		
		throw new IllegalArgumentException("Chave inexiste");
	}
	

}