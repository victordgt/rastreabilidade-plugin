package rastreabilidade.casodeuso;

import junit.framework.Assert;

import org.junit.Test;

import rastreabilidade.CasoDeUso;


public class CasoDeUsoAnotacaoTeste {
	@CasoDeUso(chave="nome", versao="versao", autor="autor")
	
	@Test
	public void testCasoDeUsoAnotacao() {
		CasoDeUso casoDeUso = (CasoDeUso)this.getClass().getMethods()[0].getAnnotations()[0];
		
		Assert.assertEquals("nome", casoDeUso.chave());
		Assert.assertEquals("versao", casoDeUso.versao());
		Assert.assertEquals("autor", casoDeUso.autor());
		
	}

}
