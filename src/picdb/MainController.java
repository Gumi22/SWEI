package picdb;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class MainController extends AbstractController {
    public TabPane tabs;
    private Stage stage;
	
	@FXML
    private Label lbStatus;
	
	public void setStage(Stage temp){
        stage = temp;
    }
	
	@FXML
	private void onBtnAbout(ActionEvent event) throws IOException {



		/*lbStatus.setText("Opening about box");
		
		FXMLLoader fl = new FXMLLoader();
        fl.setLocation(getClass().getResource("About.fxml"));
        fl.load();
        Parent root = fl.getRoot();
        
        Stage modal_dialog = new Stage(StageStyle.DECORATED);
        modal_dialog.initModality(Modality.WINDOW_MODAL);
        modal_dialog.initOwner(stage);
        Scene scene = new Scene(root, 200, 300);
       
        AboutController controller = (AboutController)fl.getController();
        controller.setStage(modal_dialog);
        modal_dialog.setScene(scene);
        modal_dialog.show();*/

        showDialog("About.fxml", "About");
	}
}
