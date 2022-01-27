package View;

import DataAccess.SaveModulDB;
import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import impl.com.calendarfx.view.NumericTextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class NewModul {
    /**
     * Neues modul.
     */
    public void neuesModul(List<Modul> Module, ListView<Button> listbox, EntityManager entityManager, EntityTransaction entityTransaction, List<Event> Events, Calendar SchoolTimeTable, Calendar StudyPlan) {
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

        Button BtSafe = getBtSafe(stage, TxtFModul, TxtFEcts, entityManager, entityTransaction, Module, listbox, Events, SchoolTimeTable, StudyPlan);

        box.getChildren().addAll(TxtModul, TxtFModul, TxtEcts, TxtFEcts, BtSafe);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setTitle("neues Modul anlegen");
        stage.show();


    }

    /**
     * Gets bt safe.
     *
     * @param stage
     *         the stage
     * @param TxtFModul
     *         the txt f modul
     * @param TxtFEcts
     *         the txt f ects
     *
     * @return the bt safe
     */
    public Button getBtSafe(Stage stage, TextField TxtFModul, TextField TxtFEcts, EntityManager entityManager, EntityTransaction entityTransaction, List<Modul> Module, ListView<Button> listbox, List<Event> Events, Calendar SchoolTimeTable, Calendar StudyPlan) {

        Button BtSafe = new Button("Speichern ");
        BtSafe.setDisable(true);

        listener(TxtFModul, TxtFEcts, BtSafe);


        BtSafe.setOnAction(
                event -> {
                    if (event.getSource() == BtSafe) {
                        Modul modul = new Modul(TxtFModul.getText(),
                                Integer.parseInt(TxtFEcts.getText()));

                        // Modul in datenbank speichern 3
                        modul.setId();
                        SaveModulDB saveModulDB = new SaveModulDB();
                        saveModulDB.insert(modul, entityManager, entityTransaction);

                        Module.add(modul);

                        Button BtModul = new Button(modul.toString2());
                        listbox.getItems().add(BtModul);

                        EditandDeleteModul editandDeleteModul = new EditandDeleteModul();
                        BtModul.setOnAction(actionEvent -> editandDeleteModul.editModul(modul, BtModul, entityManager, entityTransaction, Module, listbox, Events, SchoolTimeTable, StudyPlan));
                        stage.close();
                    }
                });
        return BtSafe;
    }

    /**
     * Listener.
     *
     * @param TxtFModul
     *         the txt f modul
     * @param TxtFEcts
     *         the txt f ects
     * @param BtSafe
     *         the bt safe
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
