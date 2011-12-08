package rastreabilidade.plugin.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IType;

/*
 * Constroi a partir das classes recuperadas em um projeto, os elementos de 
 * visualiza��o da view 
 */

public class ConstrutorArvoreView implements IGerenciadorArvore {
	
	private Map<String, List<IType>> mapa;
	private Map<String, TreeParent> mapaElementoRequisito = new HashMap<String, TreeParent>();

	public ConstrutorArvoreView(Map<String, List<IType>> mapa) {
		this.mapa = mapa;
	}
	
	@Override
	public TreeParent constroi() {
		
		Set<String> elementosRequisito = mapa.keySet();
		
		TreeParent nodoInvisivel = new TreeParent("invisivel");
		
		for (String elemento : elementosRequisito) {		
				TreeParent nodoArvore = null;
						
				nodoArvore = mapaElementoRequisito.get(elemento);
				
				if (nodoArvore == null) {
					nodoArvore = new TreeParent(elemento);
					mapaElementoRequisito.put(elemento, nodoArvore);
					nodoInvisivel.addChild(nodoArvore);
				}
				
				
				List<IType> classes = mapa.get(elemento);
				
				adicionaNodosTreeObject(nodoArvore, classes);
		}
		
		return nodoInvisivel;
	}

	/*
	 * Adiciona os elementos que representam as classes
	 */
	private void adicionaNodosTreeObject(TreeParent treeParent,
			List<IType> classes) {
		if (classes != null) {
			for (IType classe : classes) {
				TreeObject nodo = new TreeObject(classe.getElementName());
				treeParent.addChild(nodo);
				nodo.setResource(classe.getResource());
			}
				
		}
	}
	
}
