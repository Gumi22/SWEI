package picdb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutController extends AbstractController {
	private Stage stage;

	public void setStage(Stage temp) {
		stage = temp;
	}
	
	@FXML
	private void onClose(ActionEvent event) {
		stage.close();
	}
}
