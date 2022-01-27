package View;

import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ButtonAndElement {
    /**
     * Gets bt create event.
     *
     * @return the bt create event
     */
    public Button getBtCreateEvent(List<Modul> Module, List<Event> Events, Calendar StudyPlan, Calendar SchoolTimeTable, EntityManager entityManager, EntityTransaction entityTransaction) {

        Button BtCreateEvent = new Button("Erstellen eines Events");
        BtCreateEvent.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateEvent) {
                        NewEvent.createNewEvent(Module, Events, StudyPlan, SchoolTimeTable, entityManager, entityTransaction);
                    }
                });
        return BtCreateEvent;
    }

    /**
     * Gets bt create modul.
     *
     * @return the bt create modul
     */
    public Button getBtCreateModul(List<Modul> Module, ListView<Button> listbox, EntityManager entityManager, EntityTransaction entityTransaction, List<Event> Events, Calendar SchoolTimeTable, Calendar StudyPlan) {

        Button BtCreateModul = new Button("Modul anlegen");
        BtCreateModul.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateModul) {
                        NewModul newModul = new NewModul();

                        newModul.neuesModul(Module, listbox, entityManager, entityTransaction, Events, SchoolTimeTable, StudyPlan);
                    }
                });
        return BtCreateModul;
    }

    /**
     * Gets bt create moduldelte.
     *
     * @return the bt create moduldelte
     */
    public Button getBtDeleteModul(List<Modul> Module, List<Event> Events, Calendar SchoolTimeTable, Calendar StudyPlan, EntityManager entityManager, EntityTransaction entityTransaction, ListView<Button> listbox) {

        Button BtDeleteModul = new Button("Modul löschen");
        BtDeleteModul.setOnAction(
                event -> {
                    if (event.getSource() == BtDeleteModul) {
                        EditandDeleteModul editandDeleteModul = new EditandDeleteModul();
                        editandDeleteModul.modullöschen(Module, Events, SchoolTimeTable, StudyPlan, entityManager, entityTransaction, listbox);
                    }
                });
        return BtDeleteModul;
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
     *         the bt create event
     * @param BtCreateModul
     *         the bt create modul
     *
     * @return the left side split pane
     */

    public Pane getLeftSideSplitPane(Button BtCreateEvent, Button BtCreateModul, Button BtDeleteModul, ListView<Button> listbox, Button btShowQuote) {

        BorderPane BPLayoutLeft = new BorderPane();
        VBox VbButtonBox = new VBox();
        VbButtonBox.setSpacing(15);
        VbButtonBox.getChildren().addAll(BtCreateEvent, BtCreateModul, BtDeleteModul, btShowQuote);
        BPLayoutLeft.setTop(VbButtonBox);
        BPLayoutLeft.setBottom(listbox);
        Pane PBar = new Pane(BPLayoutLeft);// ist die toolbar
        VbButtonBox.setMinWidth(300);
        return PBar;
    }

}
