package View;

import Model.Event;
import Model.ICalender;
import Model.Modul;
import Model.StudyPlanGenerator;
import com.calendarfx.model.Calendar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.util.List;

public class ButtonAndElement {
    /**
     * Gets bt create event.
     *
     * @return the bt create event
     */
    public Button getBtCreateEvent(List<Modul> Module, List<Event> Events, Calendar StudyPlan, Calendar SchoolTimeTable,
            EntityManager entityManager, EntityTransaction entityTransaction) {

        Button BtCreateEvent = new Button("Erstellen eines Events");
        BtCreateEvent.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateEvent) {
                        NewEvent newEvent = new NewEvent();
                        newEvent.createNewEvent(Module, Events, StudyPlan, SchoolTimeTable, entityManager,
                                entityTransaction);
                    }
                });
        return BtCreateEvent;
    }

    /**
     * Gets bt create fillerEvent.
     *
     * @return the bt create fillerEvent
     * @author Leon
     */
    public Button getBtCreateFillerEvent(List<Modul> Module, List<Event> Events, Calendar StudyPlan,
            EntityManager entityManager, EntityTransaction entityTransaction) {
        Button BtCreateFillerEvent = new Button("Filler-Event erstellen");
        BtCreateFillerEvent.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateFillerEvent) {
                        NewEvent newEvent = new NewEvent();
                        newEvent.createNewFillerEvent(Module, Events, StudyPlan, entityManager, entityTransaction);
                    }
                });
        return BtCreateFillerEvent;
    }

    /**
     * Gets bt create modul.
     *
     * @return the bt create modul
     */
    public Button getBtCreateModul(List<Modul> Module, ListView<Button> listbox, EntityManager entityManager,
            EntityTransaction entityTransaction, List<Event> Events, Calendar SchoolTimeTable, Calendar StudyPlan) {

        Button BtCreateModul = new Button("Modul anlegen");
        BtCreateModul.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateModul) {
                        NewModul newModul = new NewModul();

                        newModul.neuesModul(Module, listbox, entityManager, entityTransaction, Events, SchoolTimeTable,
                                StudyPlan);
                    }
                });
        return BtCreateModul;
    }

    /**
     * Gets bt create moduldelte.
     *
     * @return the bt create moduldelte
     */
    public Button getBtDeleteModul(List<Modul> Module, List<Event> Events, Calendar SchoolTimeTable, Calendar StudyPlan,
            EntityManager entityManager, EntityTransaction entityTransaction, ListView<Button> listbox) {

        Button BtDeleteModul = new Button("Modul löschen");
        BtDeleteModul.setOnAction(
                event -> {
                    if (event.getSource() == BtDeleteModul) {
                        EditandDeleteModul editandDeleteModul = new EditandDeleteModul();
                        editandDeleteModul.modullöschen(Module, Events, SchoolTimeTable, StudyPlan, entityManager,
                                entityTransaction, listbox);
                    }
                });
        return BtDeleteModul;
    }

    /**
     * Gets bt generate StudyPlan.
     *
     * @return the bt generate StudyPlan
     * @author Leon
     */
    public Button getBtGenerateStudyPlan(List<Modul> module, List<Event> events, Calendar StudyPlan,
            EntityManager entityManager, EntityTransaction entityTransaction, ListView<Button> listbox) {
        Button BtGenerateStudyPlay = new Button("Lernplan generieren");
        BtGenerateStudyPlay.setOnAction(
                event -> {
                    if (event.getSource() == BtGenerateStudyPlay) {
                        StudyPlanGenerator spg = new StudyPlanGenerator(events, module, StudyPlan, entityManager,
                                entityTransaction);
                        spg.start();
                    }
                });

        return BtGenerateStudyPlay;
    }

    /**
     * Gets bt create showquote.
     *
     * @return the bt create showquote
     * @author Adrian
     */
    public Button getBtShowQuote() {
        Button BtShowQuote = new Button("Aktuelles Zitat anzeigen");
        BtShowQuote.setOnAction(
                event -> {
                    if (event.getSource() == BtShowQuote) {
                        ShowQuotes showQuotesObject = new ShowQuotes();
                        showQuotesObject.showQuotes();
                    }
                });

        return BtShowQuote;
    }

    /**
     * Gets left side split pane.
     *
     * @param BtCreateEvent
     *                      the bt create event
     * @param BtCreateModul
     *                      the bt create modul
     *
     * @param btICalExport
     * @return the left side split pane
     */

    public Pane getLeftSideSplitPane(Button BtCreateEvent, Button BtCreateFiller, Button BtCreateModul,
            Button BtDeleteModul,
            Button BtGenerateSp, ListView<Button> listbox, Button btShowQuote, Button btICalExport) {

        Pane BPLayoutLeft = new StackPane();
        VBox VbButtonBox = new VBox();
        BPLayoutLeft.getChildren().add(VbButtonBox);
        VbButtonBox.getChildren().addAll(BtCreateEvent, BtCreateFiller, BtCreateModul, BtDeleteModul, BtGenerateSp,
                btShowQuote, listbox,
                btICalExport);
        VbButtonBox.setSpacing(5);
        VBox.setVgrow(listbox, Priority.ALWAYS);
        return BPLayoutLeft;
    }

    public Button getBtICalExport(List<Event> events) {
        Button button = new Button("Kalender Exportieren");
        button.setMinWidth(200);
        button.setOnAction(
                event -> {
                    ICalender ical = new ICalender();
                    try {
                        ical.iCalenderExport(events, "./Studyplaner.ics");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Export Success");
                        alert.setHeaderText("Kalender Export erfolgreich");
                        alert.setContentText("Dateiname: Studyplaner.ics");
                        alert.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return button;
    }
}
