
/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.PlayerDelta;
import it.polito.tdp.PremierLeague.model.Simulatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGiocatoreMigliore"
    private Button btnGiocatoreMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMatch"
    private ComboBox<Match> cmbMatch; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Match match = this.cmbMatch.getValue();
    	if (match == null) {
    		this.txtResult.appendText("Non è stato inserito alcun match.\n");
    	}
    	else {
    		this.model.creaGrafo(match);
    		this.txtResult.appendText("Grafo creato correttamente.\n");
    		this.txtResult.appendText("Vertici: " + this.model.getGrafo().vertexSet().size() + "\n");
    		this.txtResult.appendText("Archi: " + this.model.getGrafo().edgeSet().size() + "\n");
    	}	
    }

    @FXML
    void doGiocatoreMigliore(ActionEvent event) {    	
    	if (this.model.getGrafo() == null) {
    		this.txtResult.appendText("Non è stato creato alcun grafo.\n");
    	}
    	else {
    		PlayerDelta bestPlayer = this.model.bestPlayer();
    		this.txtResult.appendText("Miglior giocatore:\n" + bestPlayer.getPlayer() + " con delta efficienza pari a " + bestPlayer.getDelta() + "\n");
    		
    	}
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	Match match = this.cmbMatch.getValue();
    	try {
    		Integer N = Integer.parseInt(this.txtN.getText());
    		Simulatore simulatore = new Simulatore(model, match, N);
    		simulatore.inizializza();
    		simulatore.run();
    		this.txtResult.appendText(simulatore.risultatoFinale() + "\n");
    	} catch (NumberFormatException nfe) {
    		this.txtResult.appendText("Il valore di N inserito non è valido.\n");
    	} catch (NullPointerException npe) {
    		this.txtResult.appendText("Non è stato inserito alcun N.\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGiocatoreMigliore != null : "fx:id=\"btnGiocatoreMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMatch != null : "fx:id=\"cmbMatch\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbMatch.getItems().setAll(this.model.listAllMatches());
    }
}
