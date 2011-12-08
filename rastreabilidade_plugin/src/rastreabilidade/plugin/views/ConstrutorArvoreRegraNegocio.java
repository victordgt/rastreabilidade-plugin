package rastreabilidade.plugin.views;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IType;

public class ConstrutorArvoreRegraNegocio extends ConstrutorArvoreView {

	public ConstrutorArvoreRegraNegocio(Map<String, List<IType>> mapa) {
		super(mapa);
	}
	
	@Override
	public TreeParent constroi() {
		TreeParent treeParent = super.constroi();
		treeParent.setCaminhoImagem("regra-negocio.jpg");
		return treeParent;
	}

}
