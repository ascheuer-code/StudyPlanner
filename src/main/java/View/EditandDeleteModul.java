package View;

import DataAccess.ModulDeleteDB;
import DataAccess.ModulUpdateDB;
import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import i18n.i18n;
/**
 * Class to edit and delete a modul
 */
public class EditandDeleteModul {

    /**
     * Edit modul.
     *
     * @param editModul
     *                  the edit modul
     * @param button
     *                  the button
     */
    public void editModul(Modul editModul, Button button, EntityManager entityManager,
            EntityTransaction entityTransaction, List<Modul> Module, ListView listbox, List<Event> Events,
            Calendar SchoolTimeTable, Calendar StudyPlan) {

        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        BorderPane layout = new BorderPane();
        VBox box = new VBox();

        // Texte die zur Steuerung angezeigt werden
        Text modulTxt = new Text(i18n.get("Modultext"));
        Text ectsTxt = new Text(i18n.get("ectsText"));
        // Eingabe Felder + VorhandenDaten

        TextField readModulName = new TextField(editModul.getModulname());
        TextField readEcts = new TextField(editModul.getEcts().toString2());
        // Modul in Datenbank ändern 4
        ModulUpdateDB modulUpdateDB = new ModulUpdateDB();

        Button BtEditModul = i18n.buttonForKey("BtEditModul");
        BtEditModul.setOnAction(
                event -> {
                    int index = Module.indexOf(editModul);
                    CompletableFuture.runAsync(() -> {

                        if (event.getSource() == BtEditModul) {

                            editModul.setModulname(readModulName.getText());
                            editModul.setEcts(Integer.parseInt(readEcts.getText()));
                            Module.set(index, editModul);
                            // erstellt einen anderen Button

                            modulUpdateDB.Update(editModul, entityManager, entityTransaction);

                            ArrayList<Event> temp = new ArrayList<>(Events);

                            for (Modul modul : Module) {
                                for (String uuid : modul.getUuid()) {
                                    for (Event eventa : temp) {
                                        if (uuid.equals(eventa.getId()) && editModul.equals(modul)) {
                                            SchoolTimeTable.findEntries(eventa.getTitle().trim())
                                                    .forEach(e -> e.setTitle(editModul.getModulname() + "\n"
                                                            + getEventDescription(eventa.getTitle())));
                                            StudyPlan.findEntries(eventa.getTitle().trim())
                                                    .forEach(e -> e.setTitle(editModul.getModulname() + "\n"
                                                            + getEventDescription(eventa.getTitle())));

                                        }
                                    }
                                }
                            }

                        }
                        Platform.runLater(() -> {
                            button.setText(editModul.toString2());
                            listbox.getItems().set(index, button);
                        });
                    });

                    stage.close();
                });

        box.getChildren().addAll(modulTxt, readModulName, ectsTxt, readEcts, BtEditModul);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());

    }

    /**
     * method getEventDescription, to get the event description
     * @param string
     * @return string
     */
    public String getEventDescription(String string) {
        return string.replaceAll(".*\\R", "");
    }

    /**
     * Modul löschen .
     *
     */
    public void modullöschen(List<Modul> Module, List<Event> Events, Calendar SchoolTimeTable,
            Calendar StudyPlan, EntityManager entityManager, EntityTransaction entityTransaction,
            ListView<Button> listbox) {
        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        VBox layout = new VBox();
        Scene scene = new Scene(layout);

        // Texte die zur Steuerung angezeigt werden
        Text ModulDelteTxt = new Text(i18n.get("ModulDeleteText"));
        Text ModulDeleteSure = new Text(i18n.get("ModuleDeleteSure"));
        // Button der zur Steuerung gebracuht wird
        Button BtDelete = i18n.buttonForKey("BtDelete");

        // CheckBox um sicher zugehen das es wir gelöscht werden soll
        CheckBox CBDeleteModul = i18n.checkBoxForKey("CbDeleteModul");

        ChoiceBox<Modul> chPickerModulName = getChPickerModulName(Module);

        getBtModulLöschenNachCheck(chPickerModulName, Module, CBDeleteModul, BtDelete, stage, Events, SchoolTimeTable,
                StudyPlan, entityManager, entityTransaction, listbox);

        layout.getChildren().addAll(ModulDelteTxt, chPickerModulName, ModulDeleteSure, CBDeleteModul,
                BtDelete);

        stage.setScene(scene);
        stage.setTitle(i18n.get("StTitleDelteModul"));
        stage.setWidth(300);
        stage.setHeight(200);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());

    }

    /**
     * Method to get the modul name in the choice box
     * @param modulliste
     * @return choiceBox, Modul name
     */
    public static ChoiceBox<Modul> getChPickerModulName(List<Modul> modulliste) {
        // Anfang das Feld anlegen Event

        ChoiceBox<Modul> chPickerModulName = new ChoiceBox<>();
        chPickerModulName.getItems().addAll(modulliste);

        return chPickerModulName;
    }

    /**
     * method to get the button to delete a modul
     * @param CBModulLöschen
     * @param delete
     * @param chPickerModulName
     * @param stage
     *
     * @Marc Delte Modul in DB and ListBox
     */
    private void getBtModulLöschenNachCheck(ChoiceBox<Modul> chPickerModulName, List<Modul> Module,
            CheckBox CBModulLöschen, Button delete, Stage stage, List<Event> Events,
            Calendar SchoolTimeTable, Calendar StudyPlan, EntityManager entityManager,
            EntityTransaction entityTransaction, ListView<Button> listbox) {
        delete.setOnAction(action -> {
            CompletableFuture.runAsync(() -> {
                boolean isCheck = CBModulLöschen.isSelected();

                if (isCheck == true) {
                    ArrayList<Event> temp = new ArrayList<>(Events);

                    Modul modultest = chPickerModulName.getValue();

                    for (Modul modul : Module) {
                        for (String uuid : modul.getUuid()) {
                            for (Event event : temp) {
                                if (uuid.equals(event.getId()) && modultest.equals(modul)) {
                                    SchoolTimeTable.removeEntries(SchoolTimeTable.findEntries(event.getTitle().trim()));
                                    StudyPlan.removeEntries(StudyPlan.findEntries(event.getTitle().trim()));

                                }
                            }
                        }
                    }

                    ModulDeleteDB modulDeleteDB = new ModulDeleteDB();
                    modulDeleteDB.ModulDelete(modultest, entityManager, entityTransaction);

                    Platform.runLater(() -> {

                        int index = Module.indexOf(modultest);
                        listbox.getItems().remove(index);
                        listbox.refresh();
                        Module.remove(modultest);
                    });

                }

            });

            stage.close();
        });
    }

}
