package rastreabilidade.plugin.anotacao;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class AnotacaoUtil {
	
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