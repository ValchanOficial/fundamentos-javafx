package br.com.treinaweb.agenda.repositorios.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.interfaces.IAgendaRepositorio;

public class ContatoRepositorio implements IAgendaRepositorio<Contato>{
	
	private static List<Contato> contatos = new ArrayList<Contato>();
	

	@Override
	public List<Contato> selecionar() {
		return contatos;
	}

	@Override
	public void inserir(Contato entidade) {
		contatos.add(entidade);
	}

	@Override
	public void atualizar(Contato entidade) {
		Optional<Contato> original = contatos.stream().filter(contato -> contato.getNome().equals(entidade.getNome())).findFirst();
		if(original.isPresent()) {
			original.get().setIdade(entidade.getIdade());
			original.get().setTelefone(entidade.getTelefone());
		} else {
			System.out.println("Contato não encontrado.");
		}
	}

	@Override
	public void excluir(Contato entidade) {
		contatos.remove(entidade);
	}	
}