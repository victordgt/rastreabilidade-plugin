package rastreabilidade.plugin.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IType;



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
				TreeParent nodoCasoDeUso = null;
						
				nodoCasoDeUso = mapaElementoRequisito.get(elemento);
				
				if (nodoCasoDeUso == null) {
					nodoCasoDeUso = new TreeParent(elemento);
					mapaElementoRequisito.put(elemento, nodoCasoDeUso);
					nodoInvisivel.addChild(nodoCasoDeUso);
				}
				
				
				List<IType> classes = mapa.get(elemento);
				
				if (classes != null) {
					for (IType classe : classes) {
						TreeObject nodo = new TreeObject(classe.getElementName());
						nodoCasoDeUso.addChild(nodo);
						nodo.setResource(classe.getResource());
					}
						
				}
		}
		
		return nodoInvisivel;
	}
	
}
