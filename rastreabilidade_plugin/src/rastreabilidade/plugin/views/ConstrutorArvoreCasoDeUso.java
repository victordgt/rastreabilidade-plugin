package rastreabilidade.plugin.views;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IType;

public class ConstrutorArvoreCasoDeUso extends ConstrutorArvoreView {

	public ConstrutorArvoreCasoDeUso(Map<String, List<IType>> mapa) {
		super(mapa);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public TreeParent constroi() {
		TreeParent treeParent = super.constroi();
		treeParent.setCaminhoImagem("use-case.jpg");
		return treeParent;
	}

}