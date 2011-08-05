package rastreabilidade;

import junit.framework.Assert;

import org.junit.Test;


public class CasoDeUsoAnotacaoTeste {
	@CasoDeUso(nome="nome", versao="versao", autor="autor")
	
	@Test
	public void testCasoDeUsoAnotacao() {
		CasoDeUso casoDeUso = (CasoDeUso)this.getClass().getMethods()[0].getAnnotations()[0];
		
		Assert.assertEquals("nome", casoDeUso.nome());
		Assert.assertEquals("versao", casoDeUso.versao());
		Assert.assertEquals("autor", casoDeUso.autor());
		
	}

}
