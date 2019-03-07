package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InfoScreen extends Application {
    private static String ip = "";
    private static  String name = "";
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("App");
        BorderPane pane = new BorderPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMinHeight(160);
        stage.setMinWidth(330);
        stage.show();

    }

    public static String getName() {
        return name;
    }

    public static String getIp() {
        return ip;
    }

    private void initContent(BorderPane pane) {
        GridPane gridPane = new GridPane();
        initGridPane(gridPane);
        pane.setCenter(gridPane);
        pane.setPadding(new Insets(10));


    }


    Label lblNavn, lblIP, lblY, lblX, lblDir;
    Button btnCreate;
    TextField txfNavn, txfIP, txfX, txfY, txfDir;
    ToggleGroup dir;
    int listInt = 200;



    private void initGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setGridLinesVisible(false);



        lblNavn = new Label("Navn");
        gridPane.add(lblNavn, 0, 1);



        txfNavn = new TextField();
        txfNavn.setEditable(true);
        gridPane.add(txfNavn,1,1);

    }


    public void createAction () {
        class OpretPopUp extends Stage {
            private Button btnClose;
            private GridPane gridPane = new GridPane();


            public OpretPopUp() {
                Scene scene = new Scene(gridPane);
                this.setScene(scene);
                initPopPane(gridPane);
            }

            private void initPopPane(GridPane gridPane) {
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setHgap(20);
                gridPane.setVgap(10);
                gridPane.setGridLinesVisible(false);


                lblNavn = new Label("Navn");
                gridPane.add(lblNavn, 0, 0);






                btnClose = new Button("Cancel");
                //btnClose.setOnAction(event -> closeAction());
                gridPane.add(btnClose, 2, 3);


            }



        }

        //Navn
        String name = txfNavn.getText().trim();
        if (!(name.length() > 0)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Ugyldigt navn");
            a.showAndWait();
        }

        //IP
        String ip = txfIP.getText().trim();
        //maaske et bedre IP check
        if (!(ip.length() > 6)) {
            Alert b = new Alert(Alert.AlertType.ERROR);
            b.setContentText("For kort IP");
            b.showAndWait();
        }



        OpretPopUp popup = new OpretPopUp();
        popup.showAndWait();

    }




}
