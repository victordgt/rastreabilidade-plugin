package rastreabilidade.plugin.artefato;

import rastreabilidade.RegraDeNegocio;

public class GerenciadorRegraDeNegocio extends GerenciadorArtefato {
	
	public GerenciadorRegraDeNegocio() {
		super(RegraDeNegocio.class, "identificador");
	}

}
