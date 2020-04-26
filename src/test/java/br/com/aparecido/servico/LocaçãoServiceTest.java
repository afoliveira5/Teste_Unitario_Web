
package br.com.aparecido.servico;

import static br.com.aparecido.utils.DataUtils.isMesmaData;
import static br.com.aparecido.utils.DataUtils.obterDataComDiferencaDias;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.aparecido.entidades.Filme;
import br.com.aparecido.entidades.Locação;
import br.com.aparecido.entidades.Usuário;
import br.com.aparecido.exception.FilmeSemEstoqueException;
import br.com.aparecido.exception.LocadoraException;
import br.com.aparecido.serviço.LocaçãoService;
import br.com.aparecido.utils.DataUtils;


public class LocaçãoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeLocacao() throws Exception {
		//cenario
		LocaçãoService service = new LocaçãoService();
		Usuário usuario = new Usuário("Aparecido");
		Filme filme = new Filme("Dragon Ball Z", 1, 5.0);
		
		//acao
		Locação locacao = service.alugarFilme(usuario, filme);
			
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception{
		//cenario
		LocaçãoService service = new LocaçãoService();
		Usuário usuario = new Usuário("Aparecido");
		Filme filme = new Filme("Dragon Ball Z", 0, 5.0);
		
		//acao
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException{
		//cenario
		LocaçãoService service = new LocaçãoService();
		Filme filme = new Filme("Dragon Ball Z", 1, 5.0);
		
		//acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	

	@Test
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		LocaçãoService service = new LocaçãoService();
		Usuário usuario = new Usuário("Aparecido");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//acao
		service.alugarFilme(usuario, null);
	}
}


