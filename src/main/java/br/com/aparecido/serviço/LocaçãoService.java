package br.com.aparecido.serviço;

import static br.com.aparecido.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.com.aparecido.entidades.Filme;
import br.com.aparecido.entidades.Locação;
import br.com.aparecido.entidades.Usuário;
import br.com.aparecido.exception.FilmeSemEstoqueException;
import br.com.aparecido.exception.LocadoraException;

public class LocaçãoService {

	public Locação alugarFilme(Usuário usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {
		if(usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		
		if(filme == null) {
			throw new LocadoraException("Filme vazio");
		}
		
		if(filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException();
		}
		
		Locação locacao = new Locação();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());
		
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
	
}
