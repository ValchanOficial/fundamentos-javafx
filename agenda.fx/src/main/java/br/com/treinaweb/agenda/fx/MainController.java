package br.com.treinaweb.agenda.fx;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.Impl.ContatoRepositorio;
import br.com.treinaweb.agenda.repositorios.interfaces.IAgendaRepositorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController implements Initializable{

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
	
	private Boolean btInserir;
	private Contato contatoSelecionado;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.tabelaContatos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		habilitarEdicaoAgenda(false);
//		this.tabelaContatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contato>() {
//			@Override
//			public void changed(ObservableValue<? extends Contato> observable, Contato oldValue, Contato newValue) {
//				if (newValue != null) {
//					txtNome.setText(newValue.getNome());
//					txtIdade.setText(String.valueOf(newValue.getIdade()));
//					txtTelefone.setText(newValue.getTelefone());
//				}
//			}
//		});
		this.tabelaContatos.getSelectionModel().selectedItemProperty().addListener((observador, contatoAntigo, contatoNovo) -> {
			if (contatoNovo != null) {
				txtNome.setText(contatoNovo.getNome());
				txtIdade.setText(String.valueOf(contatoNovo.getIdade()));
				txtTelefone.setText(contatoNovo.getTelefone());
				this.contatoSelecionado = contatoNovo;
			}
		});
		carregarTabelaContatos();
	}
	
	public void botaoInserir_Action() {
		this.btInserir = true;
		this.txtNome.setText("");
		this.txtIdade.setText("");
		this.txtTelefone.setText("");
		habilitarEdicaoAgenda(true);
	}
	
	public void botaoAlterar_Action() {
		habilitarEdicaoAgenda(true);
		this.btInserir = false;
		this.txtNome.setText(this.contatoSelecionado.getNome());
		this.txtIdade.setText(Integer.toString(this.contatoSelecionado.getIdade()));
		this.txtTelefone.setText(this.contatoSelecionado.getTelefone());
	}
	
	public void botaoCancelar_Action() {
		habilitarEdicaoAgenda(false);
		this.tabelaContatos.getSelectionModel().selectFirst();
	}
	
	public void botaoSalvar_Action() {
		IAgendaRepositorio<Contato> respositorioContato = new ContatoRepositorio();
		Contato contato = new Contato();
		contato.setNome(txtNome.getText());
		contato.setIdade(Integer.parseInt(txtIdade.getText()));
		contato.setTelefone(txtTelefone.getText());
		if (this.btInserir) {
			respositorioContato.inserir(contato);
		} else {
			respositorioContato.atualizar(contato);
		}
		habilitarEdicaoAgenda(false);
		carregarTabelaContatos();
		this.tabelaContatos.getSelectionModel().selectFirst();
	}
	
	public void botaoExcluir_Action() {
		Alert confirmacao = new Alert(AlertType.CONFIRMATION);
		confirmacao.setTitle("Confirmação");
		confirmacao.setHeaderText("Confirmação da exclusão do contato");
		confirmacao.setContentText("Tem certeza de que deseja excluir este contato?");
		Optional<ButtonType> resultadoConfirmacao = confirmacao.showAndWait();
		if (resultadoConfirmacao.isPresent() && resultadoConfirmacao.get() == ButtonType.OK) {
			IAgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
			repositorioContato.excluir(this.contatoSelecionado);
			carregarTabelaContatos();
			this.tabelaContatos.getSelectionModel().selectFirst();			
		}
	}
	
	private void carregarTabelaContatos() {
		IAgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
		List<Contato> contatos = repositorioContato.selecionar();
		if(contatos.isEmpty()) {
			Contato contato = new Contato();
			contato.setNome("TreinaWeb");
			contato.setIdade(12);
			contato.setTelefone("121626");
			contatos.add(contato);
		}
		ObservableList<Contato> contatosObservableList = FXCollections.observableList(contatos);
		this.tabelaContatos.getItems().setAll(contatosObservableList);
	}
	
	private void habilitarEdicaoAgenda(Boolean edicaoEstaHabilitada) {
		//Desabilitados
		this.txtNome.setDisable(!edicaoEstaHabilitada);
		this.txtIdade.setDisable(!edicaoEstaHabilitada);
		this.txtTelefone.setDisable(!edicaoEstaHabilitada);
		this.botaoCancelar.setDisable(!edicaoEstaHabilitada);
		this.botaoSalvar.setDisable(!edicaoEstaHabilitada);
		//Habilitados
		this.botaoInserir.setDisable(edicaoEstaHabilitada);
		this.botaoExcluir.setDisable(edicaoEstaHabilitada);
		this.botaoAlterar.setDisable(edicaoEstaHabilitada);
		this.tabelaContatos.setDisable(edicaoEstaHabilitada);
	}
}