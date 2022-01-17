package View;

import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class DeleteModul {

    /**
     * Modul löschen .
     *
     * @max
     */
    public void modullöschen(List Module, String NameModul, List Events, Calendar StudyPlan, Calendar SchoolTimeTable, ListView listbox) {
        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        VBox layout = new VBox();
        Scene scene = new Scene(layout);

        // Texte die zur Steuerung angezeigt werden
        Text TxtModulLöschen = new Text("Bitte das zu löschende Modul auswählen ");
        Text TxtModulLöschenQuestion = new Text("Möchten sie diese Modul wirklich löschen ? ");
        // Button der zur Steuerung gebracuht wird
        Button Btdelete = new Button("löschen");

        // CheckBox um sicher zugehen das es wir gelöscht werden soll
        CheckBox CBModulLöschen = new CheckBox("JA");

        ChoiceBox<?> test = getChPickerModulName(Module, NameModul);

        getBtModulLöschenNachCheck(CBModulLöschen,Btdelete,test,stage, Events, StudyPlan, SchoolTimeTable, listbox, Module);

        layout.getChildren().addAll(TxtModulLöschen, test, TxtModulLöschenQuestion, CBModulLöschen, Btdelete);

        stage.setScene(scene);
        stage.setTitle("Modul löschen");
        stage.setWidth(300);
        stage.setHeight(200);
        stage.show();

    }
    /**
     * Gets ch picker modul name.
     *
     * @return the ch picker modul name
     */
    public ChoiceBox<?> getChPickerModulName(List Module, String NameModul) {
        // Anfang das Feld anlegen Event

        ChoiceBox<Object> ChPickerModulName = new ChoiceBox<>();
        for (Object x : Module) {
            ChPickerModulName.getItems().addAll(x);
        }

        ChPickerModulName.setOnAction((event) -> {
            Modul x = (Modul) ChPickerModulName.getSelectionModel().getSelectedItem();
            setModulNamefuerUebergabe(x,NameModul);

        });
        return ChPickerModulName;
    }



    private void getBtModulLöschenNachCheck(CheckBox CBModulLöschen, Button Btdelete, ChoiceBox<?> chPickerModulName, Stage stage, List<Event> Events, Calendar StudyPlan, Calendar SchoolTimeTable, ListView listbox,List<Modul> Module){
        Btdelete.setOnAction(action -> {
            boolean isCheck = CBModulLöschen.isSelected();

            if (isCheck == true) {

                Module.stream().filter(e -> {

                    return e.toString().equals(chPickerModulName.getItems().get(chPickerModulName.getSelectionModel().getSelectedIndex()).toString());
                }).forEach(e -> {
                    for (String uuid : e.getUuid()) {
                        for (Event event: Events) {
                            if (event.getId().equals(uuid)) {
                                SchoolTimeTable.removeEntries(SchoolTimeTable.findEntries(event.getTitle().trim()));
                                StudyPlan.removeEntries(StudyPlan.findEntries(event.getTitle().trim()));
                            }
                        }
                    }
                });

                Module.remove(chPickerModulName.getSelectionModel().getSelectedIndex() );
                listbox.getItems().remove(chPickerModulName.getSelectionModel().getSelectedIndex() );
                listbox.refresh();
                stage.close();
            }

        });
    }
    /**
     * Sets modul namefuer uebergabe.
     *
     * @param x the x
     */
    public void setModulNamefuerUebergabe(Modul x, String NameModul) {
        NameModul = x.getModulname();

    }
}
