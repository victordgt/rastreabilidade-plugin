package rastreabilidade.plugin.views.casodeuso;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GerenciadorArvoreCasoDeUso {
	
	private Map<Annotation, List<Class<?>>> mapa;
	private Map<String, TreeParent> mapaCasosDeUso = new HashMap<String, TreeParent>();

	public GerenciadorArvoreCasoDeUso(Map<Annotation, List<Class<?>>> mapa) {
		this.mapa = mapa;
	}
	
	public TreeParent contruirArvore() {
		
		Set<Annotation> anotacoes = mapa.keySet();
		
		TreeParent nodoInvisivel = new TreeParent("invisivel");
		
		for (Annotation anotacao : anotacoes) {
			Method[] metodos = anotacao.annotationType().getMethods();
			
			try {
				TreeParent nodoCasoDeUso = null;
			
				String nomeCasoDeUso = (String)Proxy.getInvocationHandler(anotacao).invoke(anotacao, metodos[0], null);
				
				nodoCasoDeUso = mapaCasosDeUso.get(nomeCasoDeUso);
				
				if (nodoCasoDeUso == null) {
					nodoCasoDeUso = new TreeParent(nomeCasoDeUso);
					mapaCasosDeUso.put(nomeCasoDeUso, nodoCasoDeUso);
					nodoInvisivel.addChild(nodoCasoDeUso);
				}
				
				
				List<Class<?>> classes = mapa.get(anotacao);
				
				if (classes != null) {
					for (Class<?> classe : classes) {
						TreeObject nodo = new TreeObject(classe.getSimpleName());
						nodoCasoDeUso.addChild(nodo);
					}
						
				}
					
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return nodoInvisivel;
	}
	
}
