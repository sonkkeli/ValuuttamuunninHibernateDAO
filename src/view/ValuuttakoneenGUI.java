package view;

import java.text.DecimalFormat;
import controller.ValuuttakoneenOhjain;
import controller.ValuuttakoneenOhjain_IF;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Valuuttakone;
import model.Valuuttakone_IF;
/**
 * @author sonjaml 20.2.2019
 */
public class ValuuttakoneenGUI extends Application implements ValuuttakoneenGUI_IF {
	private String[] valuutat;
	private ValuuttakoneenOhjain_IF kontrolleri;
	private TextField tulosTxt;
	private ListView<String> valintaMista;
	private ListView<String> valintaMihin;
	private TextField maaraTxt;
	
	@Override
	public void init() {
		Valuuttakone_IF valuuttakone = new Valuuttakone();
		kontrolleri = new ValuuttakoneenOhjain(this,valuuttakone);
		valuutat = kontrolleri.getValuutat();
	}
	
	@Override
	public void start(Stage stage) {
		
		try {
			stage.setTitle("Valuuttakone");
			
			Button muunna = new Button("Muunna");
			muunna.setOnAction(new EventHandler<ActionEvent>() {
	        	@Override
	            public void handle(ActionEvent event) {
	                kontrolleri.muunnos();
	            }
	        });		

			maaraTxt = new TextField();
			Label maaraLabel = new Label("Määrä");
			Label mista = new Label("Mistä");
			Label mihin = new Label("Mihin");			
			Label tulosLabel = new Label("Tulos");
			tulosTxt = new TextField();
			
			VBox sarake2 = new VBox();
			sarake2.setSpacing(10);
			sarake2.getChildren().addAll(maaraTxt, muunna, tulosLabel, tulosTxt);
			
			valintaMista = new ListView<>();
			valintaMihin = new ListView<>();
			
			ObservableList<String> itemsMista = FXCollections.observableArrayList(valuutat);
			ObservableList<String> itemsMihin = FXCollections.observableArrayList(valuutat);
						
			valintaMista.setItems(itemsMista);	
			valintaMihin.setItems(itemsMihin);
			valintaMista.setMaxHeight(150);
			valintaMihin.setMaxHeight(150);
			valintaMista.setMaxWidth(165);
			valintaMihin.setMaxWidth(165);
			
			GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setPadding(new Insets(10,10,10,10));
	        grid.setVgap(10);
	        grid.setHgap(10);
	        grid.add(mista, 0, 0); // sarake, rivi
	        grid.add(mihin, 1, 0);
	        grid.add(maaraLabel, 2, 0);
	        grid.add(valintaMista, 0, 1);
	        grid.add(valintaMihin, 1, 1);
	        grid.add(sarake2, 2, 1);
	        
	        Scene scene = new Scene(grid, 550, 200);
	        stage.setScene(scene);
	        stage.show();
	        
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Määräkenttään kelpuutetaan vain numero");
		}		
	}

	@Override
	public int getLähtöIndeksi() {
		return valintaMista.getSelectionModel().getSelectedIndex();
	}

	@Override
	public int getKohdeIndeksi() {
		return valintaMihin.getSelectionModel().getSelectedIndex();
	}

	@Override
	public double getMäärä() {
		return Double.parseDouble(maaraTxt.getText());
	}

	@Override
	public void setTulos(double määrä) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		tulosTxt.setText(formatter.format(määrä));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
