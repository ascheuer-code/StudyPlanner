package View;

import DataAccess.SaveModulDB;
import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import impl.com.calendarfx.view.NumericTextField;
import javafx.application.Platform;
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
import i18n.i18n;

/**
 * the class for a new modul
 */
public class NewModul {

    /**
     * method neuesModul to set the window for a new modul
     * @param Module
     * @param listbox
     * @param entityManager
     * @param entityTransaction
     * @param Events
     * @param SchoolTimeTable
     * @param StudyPlan
     */
    public void neuesModul(List<Modul> Module, ListView<Button> listbox, EntityManager entityManager,
            EntityTransaction entityTransaction, List<Event> Events, Calendar SchoolTimeTable, Calendar StudyPlan) {
        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        BorderPane layout = new BorderPane();
        VBox box = new VBox();

        // Texte die zur Steuerung angezeigt werden
        Text ModulTxt = new Text(i18n.get("Modultext"));
        Text EctsTxt = new Text(i18n.get("ectsText"));
        // Eingabe Felder

        TextField TxtFModul = new TextField();
        TextField TxtFEcts = new NumericTextField();

        Button BtSafe = getBtSafe(stage, TxtFModul, TxtFEcts, entityManager, entityTransaction, Module, listbox, Events,
                SchoolTimeTable, StudyPlan);

        box.getChildren().addAll(ModulTxt, TxtFModul, EctsTxt, TxtFEcts, BtSafe);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setTitle(i18n.get("StTitleNewModul"));
        stage.show();
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());

    }

    /**
     * Gets button for safe.
     *
     * @param stage
     *                  the stage
     * @param TxtFModul
     *                  the txt f modul
     * @param TxtFEcts
     *                  the txt f ects
     *
     * @return the safe button
     */
    public Button getBtSafe(Stage stage, TextField TxtFModul, TextField TxtFEcts, EntityManager entityManager,
            EntityTransaction entityTransaction, List<Modul> Module, ListView<Button> listbox, List<Event> Events,
            Calendar SchoolTimeTable, Calendar StudyPlan) {

        Button BtSave = i18n.buttonForKey("BtSave");
        BtSave.setDisable(true);

        listener(TxtFModul, TxtFEcts, BtSave);

        BtSave.setOnAction(
                event -> {
                    Platform.runLater(() -> {
                        if (event.getSource() == BtSave) {
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
                            BtModul.setOnAction(
                                    actionEvent -> editandDeleteModul.editModul(modul, BtModul, entityManager,
                                            entityTransaction, Module, listbox, Events, SchoolTimeTable, StudyPlan));

                        }

                    });
                    stage.close();
                });
        return BtSave;
    }

    /**
     * Listener.
     *
     * @param TxtFModul
     *                  the txt f modul
     * @param TxtFEcts
     *                  the txt f ects
     * @param BtSafe
     *                  the bt safe
     */
    public void listener(TextField TxtFModul, TextField TxtFEcts, Button BtSafe) {

        TxtFModul.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                BtSafe.setDisable(true);
            } else if (!TxtFEcts.getText().equals("")) {
                BtSafe.setDisable(false);

            } else {
                TxtFEcts.textProperty().addListener(
                        (observableValue1, oldValue1, newValue1) -> BtSafe.setDisable(newValue1.trim().isEmpty()));

            }
        });
    }
}
