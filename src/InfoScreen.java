package src;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InfoScreen extends Stage  {
    TextField txfName;

    public InfoScreen(String title) {
    	 initStyle(StageStyle.UTILITY);
         setMinHeight(100);
         setMinWidth(200);
         setResizable(false);
         setTitle(title);
         GridPane pane = new GridPane();
         initContent(pane);
         Scene scene = new Scene(pane);
         setScene(scene);
    }
 
        public void initContent(GridPane pane) {
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(20));

        Label lblName = new Label("Navn:");
        pane.add(lblName, 0, 0);
        
        txfName = new TextField();
        pane.add(txfName, 1, 0);
        
        Button btnKlar = new Button("Klar");
        pane.add(btnKlar, 1, 2);
        btnKlar.setOnAction(event -> this.klarAction());
        
        Button btnStart = new Button("Start!");
        pane.add(btnStart, 2, 2);
        btnStart.setOnAction(event -> this.startAction());
        
    }
        
        private void klarAction() {
    		try {
                Main.connectToServer();
                System.out.println("Connected infoscreen");
            }
            catch (Exception e) {
                System.out.println("Inforscreen cant connect to server");
            }
            String[] s = txfName.getText().split(" ");
            Main.playername = s[0];
            Main.sendName();
            System.out.println("From InfoScreen: " + s[0]);
        }
	
	private void startAction() {
		try {
			Main.outToServer.writeBytes("Ready" + '\n');
		}
		catch (IOException e) {
			System.out.println("Infoscreen objected to starting the game");
		}
	}
    
         
       

}
