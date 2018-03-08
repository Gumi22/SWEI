package picdb;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application implements BIF.SWE2.interfaces.Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fl = new FXMLLoader();
	        fl.setLocation(getClass().getResource("Main.fxml"));
	        fl.load();

	        BorderPane root = (BorderPane)fl.getRoot();
			MainController controller = (MainController)fl.getController();
			
			controller.setStage(primaryStage);

			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		/*try {
			DataAccessLayerImpl.getInstance().getPictures("lol",null,null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		launch(args);
	}

	@Override
	public void helloWorld() {
		// Do nothing, this will test the junit test setup		
	}
}
