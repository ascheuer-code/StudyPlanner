package View;

import Model.Modul;
import com.studyplanner.code_ascheuer.github.StudyPlanner;
import impl.com.calendarfx.view.NumericTextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NewModulAndConfig {
    /**
     * Gets bt safe.
     *
     * @param stage     the stage
     * @param TxtFModul the txt f modul
     * @param TxtFEcts  the txt f ects
     * @return the bt safe
     */
    public Button getBtSafe(Stage stage, TextField TxtFModul, TextField TxtFEcts, ListView listbox, List Module) {

        Button BtSafe = new Button("Speichern ");
        BtSafe.setDisable(true);

        listener(TxtFModul, TxtFEcts, BtSafe);


        BtSafe.setOnAction(
                event -> {
                    if (event.getSource() == BtSafe) {
                        Modul modul = new Modul(TxtFModul.getText(),
                                Integer.parseInt(TxtFEcts.getText()));


                        Module.add(modul);

                        Button BtModul = new Button(modul.toString());
                        listbox.getItems().add(BtModul);

                        BtModul.setOnAction(actionEvent -> editModul(modul, BtModul, listbox, Module));
                        stage.close();
                    }
                });
        return BtSafe;
    }

    /**
     * Neues modul.
     */
    public void neuesModul(ListView listbox, List Module) {
        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        BorderPane layout = new BorderPane();
        VBox box = new VBox();

        // Texte die zur Steuerung angezeigt werden
        Text TxtModul = new Text("Modulname :");
        Text TxtEcts = new Text("Ects Wert des Moduls:");
        // Eingabe Felder

        TextField TxtFModul = new TextField();
        TextField TxtFEcts = new NumericTextField();

        Button BtSafe = getBtSafe(stage, TxtFModul, TxtFEcts, listbox, Module);

        box.getChildren().addAll(TxtModul, TxtFModul, TxtEcts, TxtFEcts, BtSafe);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setTitle("neues Modul anlegen");
        stage.show();


    }

    /**
     * Edit modul.
     *
     * @param editModul the edit modul
     * @param button    the button
     */
    public void editModul(Modul editModul, Button button, ListView listbox, List Module) {

        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        BorderPane layout = new BorderPane();
        VBox box = new VBox();


        // Texte die zur Steuerung angezeigt werden
        Text modulText = new Text("Modulname :");
        Text etcText = new Text("Ects Wert des Moduls:");
        // Eingabe Felder + VorhandenDaten
        TextField readModulName = new TextField(editModul.getModulname());
        TextField readEcts = new TextField(editModul.getEcts().toString2());

        Button BtEditModul = new Button("Ändern ");
        BtEditModul.setOnAction(
                event -> {
                    if (event.getSource() == BtEditModul) {
                        int index = Module.indexOf(editModul);
                        editModul.setModulname(readModulName.getText());
                        editModul.setEcts(Integer.parseInt(readEcts.getText()));
                        Module.set(index, editModul);
                        //erstellt einen anderen Button

                        button.setText(editModul.toString());

                        listbox.getItems().set(index, button);


                    }
                    stage.setTitle("Modul bearbeiten");
                    stage.close();
                });


        box.getChildren().addAll(modulText, readModulName, etcText, readEcts, BtEditModul);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Listener.
     *
     * @param TxtFModul the txt f modul
     * @param TxtFEcts  the txt f ects
     * @param BtSafe    the bt safe
     */
    public void listener(TextField TxtFModul, TextField TxtFEcts, Button BtSafe) {

        TxtFModul.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                BtSafe.setDisable(true);
            } else if (!TxtFEcts.getText().equals("")) {
                BtSafe.setDisable(false);


            } else {
                TxtFEcts.textProperty().addListener((observableValue1, oldValue1, newValue1) -> BtSafe.setDisable(newValue1.trim().isEmpty()));

            }
        });
    }
}

