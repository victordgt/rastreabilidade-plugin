package rastreabilidade.plugin.views.regradenegocio;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rastreabilidade.plugin.views.IGerenciadorArvore;
import rastreabilidade.plugin.views.TreeObject;
import rastreabilidade.plugin.views.TreeParent;


public class GerenciadorArvoreRegraDeNegocio implements IGerenciadorArvore {
	
	private Map<Annotation, List<Class<?>>> mapa;
	private Map<String, TreeParent> mapaRegraDeNegocio = new HashMap<String, TreeParent>();

	public GerenciadorArvoreRegraDeNegocio(Map<Annotation, List<Class<?>>> mapa) {
		this.mapa = mapa;
	}
	
	@Override
	public TreeParent contruirArvore() {
		
		Set<Annotation> anotacoes = mapa.keySet();
		
		TreeParent nodoInvisivel = new TreeParent("invisivel");
		
		for (Annotation anotacao : anotacoes) {
			Method[] metodos = anotacao.annotationType().getMethods();
			
			try {
				TreeParent nodoRegraDeNegocio = null;
			
				//Recupera por meio do uso de um proxy
				String nomeRegraDeNegocio = (String)Proxy.getInvocationHandler(anotacao).invoke(anotacao, metodos[0], null);
				
				nodoRegraDeNegocio = mapaRegraDeNegocio.get(nomeRegraDeNegocio);
				
				if (nodoRegraDeNegocio == null) {
					nodoRegraDeNegocio = new TreeParent(nomeRegraDeNegocio);
					mapaRegraDeNegocio.put(nomeRegraDeNegocio, nodoRegraDeNegocio);
					nodoInvisivel.addChild(nodoRegraDeNegocio);
				}
				
				
				List<Class<?>> classes = mapa.get(anotacao);
				
				if (classes != null) {
					for (Class<?> classe : classes) {
						TreeObject nodo = new TreeObject(classe.getSimpleName());
						nodoRegraDeNegocio.addChild(nodo);
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
