package rastreabilidade.plugin.artefato;

import org.eclipse.swt.widgets.Shell;

import rastreabilidade.RegraDeNegocio;

public class GerenciadorRegraDeNegocio extends GerenciadorArtefato {
	
	public GerenciadorRegraDeNegocio(Shell shell) {
		super(RegraDeNegocio.class, "chave", shell);
	}

}
