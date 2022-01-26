package View;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;
/**
 * This class is for the quotes
 *
 * @author Adrian
 */

public class ShowQuotes {



    /**
     * Here are the quotes saved
     * and this method needed to throw the quotes out.
     *
     * @Adrian
     */
    public void showQuotes() {
        //List for the quotes in german
        List<String> gutzuwissen = new ArrayList<String>();

        //List for the button contents
        List<String> buttoncontents = new ArrayList<String>();

        //To throw randomly
        Random numberGerman = new Random();
        int zufallszahl = numberGerman.nextInt(55);
        int randomnumber = numberGerman.nextInt(3);

        //Quotes in german [IMPORTANT:IF YOU ADD SMK ;THINK ABOUT zufallszahl to update]
        gutzuwissen.add("\"Bildung ist etwas Wunderbares. Doch sollte man sich von Zeit zu Zeit daran erinnern, dass wirklich Wissenswertes nicht gelehrt werden kann.\"");
        gutzuwissen.add("\"Niemand kriegt beim ersten Mal alles richtig hin. Was uns ausmacht, ist, wie wir aus unseren Fehlern lernen.\"");
        gutzuwissen.add("\"Das Glück des Lebens besteht nicht darin, wenig oder keine Schwierigkeiten zu haben, sondern sie alle siegreich und glorreich zu überwinden.\"");
        gutzuwissen.add("\"Mach dir keine Sorgen wegen deiner Schwierigkeiten mit der Mathematik. Ich kann dir versichern, dass meine noch größer sind.\"");
        gutzuwissen.add("\"Hindernisse und Schwierigkeiten sind Stufen, auf denen wir in die Höhe steigen.\"");
        gutzuwissen.add("\"Der größte Feind des Fortschritts ist nicht der Irrtum, sondern die Trägheit.\"");
        gutzuwissen.add("\"Es ist keine Schande nichts zu wissen, wohl aber, nichts lernen zu wollen.\"");
        gutzuwissen.add("\"Wer aufhört, besser zu werden, hat aufgehört, gut zu sein.\"");
        gutzuwissen.add("\"Lernen ist wie Rudern gegen den Strom. Hört man damit auf, treibt man zurück.\"");
        gutzuwissen.add("\"So wie das Eisen außer Gebrauch rostet und das still stehende Wasser verdirbt oder bei Kälte gefriert, so verkommt der Geist ohne Übung.\"");
        gutzuwissen.add("\"Das beste Training liegt immer noch im selbständigen Machen.\"");
        gutzuwissen.add("\"Es ist nicht genug zu wissen – man muss auch anwenden. Es ist nicht genug zu wollen – man muss auch tun.\"");
        gutzuwissen.add("\"Es gibt keinen Misserfolg. Entweder du hast Erfolg oder du lernst.\"");
        gutzuwissen.add("\"Wem der Himmel eine große Aufgabe zugedacht hat, dessen Herz und Willen zermürbt er erst durch Leid.\"");
        gutzuwissen.add("\"Es ist nicht der Berg, den wir bezwingen – wir bezwingen uns selbst.\"");
        gutzuwissen.add("\"Wer über gewisse Dinge den Verstand nicht verliert, der hat keinen zu verlieren.\"");
        gutzuwissen.add("\"Der Verstand ist wie eine Fahrkarte: Sie hat nur dann einen Sinn, wenn sie benutzt wird.\"");
        gutzuwissen.add("\"Ein ungeübtes Gehirn ist schädlicher für die Gesundheit als ein ungeübter Körper.\"");
        gutzuwissen.add("\"So ein bisschen Bildung ziert den ganzen Menschen.\"");
        gutzuwissen.add("\"Ein Buch ist ein Spiegel, wenn ein Affe hineinsieht, so kann kein Apostel herausgucken.\"");
        gutzuwissen.add("\"Die Bildung kommt nicht vom Lesen, sondern vom Nachdenken über das Gelesene.\"");
        gutzuwissen.add("\"Wer zu lesen versteht, besitzt den Schlüssel zu großen Taten, zu unerträumten Möglichkeiten.\"");
        gutzuwissen.add("\"Auch eine schwere Tür hat nur einen kleinen Schlüssel nötig.\"");
        gutzuwissen.add("\"Nicht weil es schwer ist, wagen wir es nicht, sondern weil wir es nicht wagen, ist es schwer.\"");
        gutzuwissen.add("\"Ich habe keine Angst vor Stürmen. Ich lerne, wie ich mein Schiff steuern muss.\"");
        gutzuwissen.add("\"Der Erfolgreiche lernt aus seinen Fehlern und wird auf neuen Wegen von vorne beginnen.\"");
        gutzuwissen.add("\"Suche nicht nach Fehlern, suche nach Lösungen.\"");
        gutzuwissen.add("\"Die Lösung ist immer einfach, man muss sie nur finden.\"");
        gutzuwissen.add("\"Alle Dinge sind schwer bevor sie leicht werden.\"");
        gutzuwissen.add("\"Aus Niederlagen lernt man leicht. Schwieriger ist es, aus Siegen zu lernen.\"");
        gutzuwissen.add("\"Ein Kopf ohne Gedächtnis ist eine Festung ohne Besatzung.\"");
        gutzuwissen.add("\"Eine Wahrheit kann erst wirken, wenn der Empfänger für sie reif ist.\"");
        gutzuwissen.add("\"Habe Mut, dich deines eigenen Verstandes zu bedienen.\"");
        gutzuwissen.add("\"Es ist zweierlei, Verstand empfangen haben, und den Verstand, den man empfangen hat, auch anzuwenden gelernt haben.\"");
        gutzuwissen.add("\"Gerade Leute, die nichts lernen, lernen auch nichts daraus, dass sie nichts gelernt haben.\"");
        gutzuwissen.add("\"Der Mensch soll lernen, nur die Ochsen büffeln.\"");
        gutzuwissen.add("\"Die Wiederholung ist die Mutter – nicht bloß des Studierens, sondern auch der Bildung.\"");
        gutzuwissen.add("\"Bildung ist das, was übrig bleibt, wenn wir vergessen, was wir gelernt haben.\"");
        gutzuwissen.add("\"Sage es mir, und ich werde es vergessen. Zeige es mir, und ich werde es vielleicht behalten. Lass es mich tun, und ich werde es können.\"");
        gutzuwissen.add("\"Wenn du es nicht versuchst, wirst du nie wissen, ob du es kannst.\"");
        gutzuwissen.add("\"Ich habe keine besondere Begabung, sondern bin nur leidenschaftlich neugierig.\"");
        gutzuwissen.add("\"Die Neugier steht immer an erster Stelle eines Problems, das gelöst werden will.\"");
        gutzuwissen.add("\"Begeisterung ist Dünger für das Gehirn.\"");
        gutzuwissen.add("\"Das Gehirn ist ein Organ, mit dem wir denken, dass wir denken.\"");
        gutzuwissen.add("\"Der Kopf ist rund, damit das Denken die Richtung ändern kann.\"");
        gutzuwissen.add("\"Wer Stroh im Kopf hat, fürchtet den Funken der Wahrheit.\"");
        gutzuwissen.add("\"Wie du gesät hast, so wirst du ernten.\"");
        gutzuwissen.add("\"Man muss viel gelernt haben, um über das, was man nicht weiß, fragen zu können.\"");
        gutzuwissen.add("\"Der größte Feind des Wissens ist nicht Ignoranz, sondern die Illusion, wissend zu sein.\"");
        gutzuwissen.add("\"Von dem was wir noch nicht wissen können wir am meisten lernen.\"");
        gutzuwissen.add("\"Lebe als würdest du morgen sterben. Lerne als würdest du für immer leben.\"");
        gutzuwissen.add("\"Bildung ist etwas Wunderbares. Doch sollte man sich von Zeit zu Zeit daran erinnern, dass wirklich Wissenswertes nicht gelehrt werden kann.\"");
        gutzuwissen.add("\"Lerne für dein Leben, aber verlerne darüber nicht, zu leben.\"");
        gutzuwissen.add("\"In drei Worten kann ich alles zusammenfassen, was ich über das Leben gelernt: Es geht weiter.\"");
        gutzuwissen.add("\"Auch Umwege erweitern unseren Horizont.\"");
        gutzuwissen.add("\"Wer Erfolg haben will, muss lernen, dass Misserfolge gesunde, unvermeidliche Schritte auf dem Weg nach ganz oben sind.\"");

        //Button contents [IMPORTANT:IF YOU ADD SMK ;THINK ABOUT randomnumber to update]
        buttoncontents.add("Alles klar");
        buttoncontents.add("Okay");
        buttoncontents.add("Super Zitat!");



        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        VBox layout = new VBox();
        Scene scene = new Scene(layout);



        Text Zitat = new Text(gutzuwissen.get(zufallszahl));
        Button checkout = new Button(buttoncontents.get(randomnumber));

        layout.getChildren().addAll( Zitat, checkout);

        stage.setScene(scene);
        stage.setTitle("Aktuelles Zitat");
        stage.setWidth(500);
        stage.setHeight(100);
        stage.show();
        checkout.setOnAction(action ->{stage.close();});


    }
}
