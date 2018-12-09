package br.com.treinaweb.agenda.fx;

import br.com.treinaweb.agenda.entidades.Contato;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

	@FXML
	private TableView<Contato> tabelaContatos;
	@FXML
	private Button botaoInserir;
	@FXML
	private Button botaoAlterar;
	@FXML
	private Button botaoExcluir;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtIdade;
	@FXML
	private TextField txtTelefone;
	@FXML
	private Button botaoSalvar;
	@FXML
	private Button botaoCancelar;
	
	
}