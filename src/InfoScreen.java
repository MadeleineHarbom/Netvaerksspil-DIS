package src;

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
        
        Label lblIPAddress = new Label("IPv4 Adrdress:");
        pane.add(lblIPAddress, 0, 1);
        
        txfName = new TextField();
        pane.add(txfName, 1, 0);
        
        TextField txfIPAddress = new TextField();
        pane.add(txfIPAddress, 1, 1);
        
        Button btnOkay = new Button("Okay");
        pane.add(btnOkay, 1, 2);
        btnOkay.setOnAction(event -> this.okayAction());
        
    }


    private void okayAction() {
        String[] s = txfName.getText().split(" ");
        Main.name = s[0];
        System.out.println("From InfoScreen: " + s[0]);
        hide();
    }
    
         
       

}
