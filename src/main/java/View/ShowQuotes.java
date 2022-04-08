package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import i18n.i18n;

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
         * @author Adrian
         */
        public void showQuotes() {

                        // List for the quotes in german
                        List<String> quotesGerman = new ArrayList<>();

                        //List for the quotes in english
                        List<String> quotesEnglish = new ArrayList<>();

                        // List for the button contents
                        List<String> btContent = new ArrayList<>();

                        // To throw randomly
                        Random random = new Random();
                        int randomNumber = random.nextInt(55);    // For the Quotes
                        int BtRandom = random.nextInt(3);    // For the textfields

                        // Quotes in german [IMPORTANT:IF YOU ADD SMK ;THINK ABOUT zufallszahl to
                        // update]
                        quotesGerman.add("\"Bildung ist etwas Wunderbares. Doch sollte man sich von Zeit zu Zeit daran erinnern, dass wirklich Wissenswertes nicht gelehrt werden kann.\"");
                        quotesGerman.add("\"Niemand kriegt beim ersten Mal alles richtig hin. Was uns ausmacht, ist, wie wir aus unseren Fehlern lernen.\"");
                        quotesGerman.add("\"Das Glück des Lebens besteht nicht darin, wenig oder keine Schwierigkeiten zu haben, sondern sie alle siegreich und glorreich zu überwinden.\"");
                        quotesGerman.add("\"Mach dir keine Sorgen wegen deiner Schwierigkeiten mit der Mathematik. Ich kann dir versichern, dass meine noch größer sind.\"");
                        quotesGerman.add("\"Hindernisse und Schwierigkeiten sind Stufen, auf denen wir in die Höhe steigen.\"");
                        quotesGerman.add("\"Der größte Feind des Fortschritts ist nicht der Irrtum, sondern die Trägheit.\"");
                        quotesGerman.add("\"Es ist keine Schande nichts zu wissen, wohl aber, nichts lernen zu wollen.\"");
                        quotesGerman.add("\"Wer aufhört, besser zu werden, hat aufgehört, gut zu sein.\"");
                        quotesGerman.add("\"Lernen ist wie Rudern gegen den Strom. Hört man damit auf, treibt man zurück.\"");
                        quotesGerman.add("\"So wie das Eisen außer Gebrauch rostet und das still stehende Wasser verdirbt oder bei Kälte gefriert, so verkommt der Geist ohne Übung.\"");
                        quotesGerman.add("\"Das beste Training liegt immer noch im selbständigen Machen.\"");
                        quotesGerman.add("\"Es ist nicht genug zu wissen – man muss auch anwenden. Es ist nicht genug zu wollen – man muss auch tun.\"");
                        quotesGerman.add("\"Es gibt keinen Misserfolg. Entweder du hast Erfolg oder du lernst.\"");
                        quotesGerman.add("\"Wem der Himmel eine große Aufgabe zugedacht hat, dessen Herz und Willen zermürbt er erst durch Leid.\"");
                        quotesGerman.add("\"Es ist nicht der Berg, den wir bezwingen – wir bezwingen uns selbst.\"");
                        quotesGerman.add("\"Wer über gewisse Dinge den Verstand nicht verliert, der hat keinen zu verlieren.\"");
                        quotesGerman.add("\"Der Verstand ist wie eine Fahrkarte: Sie hat nur dann einen Sinn, wenn sie benutzt wird.\"");
                        quotesGerman.add("\"Ein ungeübtes Gehirn ist schädlicher für die Gesundheit als ein ungeübter Körper.\"");
                        quotesGerman.add("\"So ein bisschen Bildung ziert den ganzen Menschen.\"");
                        quotesGerman.add("\"Ein Buch ist ein Spiegel, wenn ein Affe hineinsieht, so kann kein Apostel herausgucken.\"");
                        quotesGerman.add("\"Die Bildung kommt nicht vom Lesen, sondern vom Nachdenken über das Gelesene.\"");
                        quotesGerman.add("\"Wer zu lesen versteht, besitzt den Schlüssel zu großen Taten, zu unerträumten Möglichkeiten.\"");
                        quotesGerman.add("\"Auch eine schwere Tür hat nur einen kleinen Schlüssel nötig.\"");
                        quotesGerman.add("\"Nicht weil es schwer ist, wagen wir es nicht, sondern weil wir es nicht wagen, ist es schwer.\"");
                        quotesGerman.add("\"Ich habe keine Angst vor Stürmen. Ich lerne, wie ich mein Schiff steuern muss.\"");
                        quotesGerman.add("\"Der Erfolgreiche lernt aus seinen Fehlern und wird auf neuen Wegen von vorne beginnen.\"");
                        quotesGerman.add("\"Suche nicht nach Fehlern, suche nach Lösungen.\"");
                        quotesGerman.add("\"Die Lösung ist immer einfach, man muss sie nur finden.\"");
                        quotesGerman.add("\"Alle Dinge sind schwer bevor sie leicht werden.\"");
                        quotesGerman.add("\"Aus Niederlagen lernt man leicht. Schwieriger ist es, aus Siegen zu lernen.\"");
                        quotesGerman.add("\"Ein Kopf ohne Gedächtnis ist eine Festung ohne Besatzung.\"");
                        quotesGerman.add("\"Eine Wahrheit kann erst wirken, wenn der Empfänger für sie reif ist.\"");
                        quotesGerman.add("\"Habe Mut, dich deines eigenen Verstandes zu bedienen.\"");
                        quotesGerman.add("\"Es ist zweierlei, Verstand empfangen haben, und den Verstand, den man empfangen hat, auch anzuwenden gelernt haben.\"");
                        quotesGerman.add("\"Gerade Leute, die nichts lernen, lernen auch nichts daraus, dass sie nichts gelernt haben.\"");
                        quotesGerman.add("\"Der Mensch soll lernen, nur die Ochsen büffeln.\"");
                        quotesGerman.add("\"Die Wiederholung ist die Mutter – nicht bloß des Studierens, sondern auch der Bildung.\"");
                        quotesGerman.add("\"Bildung ist das, was übrig bleibt, wenn wir vergessen, was wir gelernt haben.\"");
                        quotesGerman.add("\"Sage es mir, und ich werde es vergessen. Zeige es mir, und ich werde es vielleicht behalten. Lass es mich tun, und ich werde es können.\"");
                        quotesGerman.add("\"Wenn du es nicht versuchst, wirst du nie wissen, ob du es kannst.\"");
                        quotesGerman.add("\"Ich habe keine besondere Begabung, sondern bin nur leidenschaftlich neugierig.\"");
                        quotesGerman.add("\"Die Neugier steht immer an erster Stelle eines Problems, das gelöst werden will.\"");
                        quotesGerman.add("\"Begeisterung ist Dünger für das Gehirn.\"");
                        quotesGerman.add("\"Das Gehirn ist ein Organ, mit dem wir denken, dass wir denken.\"");
                        quotesGerman.add("\"Der Kopf ist rund, damit das Denken die Richtung ändern kann.\"");
                        quotesGerman.add("\"Wer Stroh im Kopf hat, fürchtet den Funken der Wahrheit.\"");
                        quotesGerman.add("\"Wie du gesät hast, so wirst du ernten.\"");
                        quotesGerman.add("\"Man muss viel gelernt haben, um über das, was man nicht weiß, fragen zu können.\"");
                        quotesGerman.add("\"Der größte Feind des Wissens ist nicht Ignoranz, sondern die Illusion, wissend zu sein.\"");
                        quotesGerman.add("\"Von dem was wir noch nicht wissen können wir am meisten lernen.\"");
                        quotesGerman.add("\"Lebe als würdest du morgen sterben. Lerne als würdest du für immer leben.\"");
                        quotesGerman.add("\"Bildung ist etwas Wunderbares. Doch sollte man sich von Zeit zu Zeit daran erinnern, dass wirklich Wissenswertes nicht gelehrt werden kann.\"");
                        quotesGerman.add("\"Lerne für dein Leben, aber verlerne darüber nicht, zu leben.\"");
                        quotesGerman.add("\"In drei Worten kann ich alles zusammenfassen, was ich über das Leben gelernt: Es geht weiter.\"");
                        quotesGerman.add("\"Auch Umwege erweitern unseren Horizont.\"");
                        quotesGerman.add("\"Wer Erfolg haben will, muss lernen, dass Misserfolge gesunde, unvermeidliche Schritte auf dem Weg nach ganz oben sind.\"");

                        // Quotes in english [IMPORTANT:IF YOU ADD SMK ;THINK ABOUT randomnumber to
                        // update]
                        quotesEnglish.add("\"Education is something wonderful. But it should be remembered from time to time that what is truly worth knowing cannot be taught.\"");
                        quotesEnglish.add("\"Nobody gets everything right the first time. What sets us apart is how we learn from our mistakes.\"");
                        quotesEnglish.add("\"The happiness of life is not to have few or no difficulties, but to overcome them all victoriously and gloriously.\"");
                        quotesEnglish.add("\"Don't worry about your difficulties with math. I can assure you that mine are even greater.\"");
                        quotesEnglish.add("\"Obstacles and difficulties are steps on which we climb up.\"");
                        quotesEnglish.add("\"The greatest enemy of progress is not error, but inertia.\"");
                        quotesEnglish.add("\"There is no shame in not knowing, but there is in not wanting to learn.\"");
                        quotesEnglish.add("\"If you stop getting better, you've stopped being good.\"");
                        quotesEnglish.add("\"Learning is like rowing against the current. If you stop, you drift back.\"");
                        quotesEnglish.add("\"Just as iron rusts out of use and stagnant water spoils or freezes in the cold, so the mind degenerates without practice.\"");
                        quotesEnglish.add("\"The best training is still in doing it on your own.\"");
                        quotesEnglish.add("\"It is not enough to know - one must also apply. It is not enough to want - one must also do.\"");
                        quotesEnglish.add("\"There is no such thing as failure. Either you succeed or you learn.\"");
                        quotesEnglish.add("\"To whom Heaven has assigned a great task, He first wears down his heart and will through suffering.\"");
                        quotesEnglish.add("\"It is not the mountain that we conquer - we conquer ourselves.\"");
                        quotesEnglish.add("\"If you don't lose your mind over certain things, you have none to lose.\"");
                        quotesEnglish.add("\"The mind is like a ticket: it has meaning only when it is used.\"");
                        quotesEnglish.add("\"An untrained brain is more harmful to health than an untrained body.\"");
                        quotesEnglish.add("\"Such a little education adorns the whole person.\"");
                        quotesEnglish.add("\"A book is a mirror, if a monkey looks into it, no apostle can look out.\"");
                        quotesEnglish.add("\"Education does not come from reading, but from thinking about what you read.\"");
                        quotesEnglish.add("\"Those who know how to read hold the key to great deeds, to undreamed-of possibilities.\"");
                        quotesEnglish.add("\"Even a heavy door only needs a small key.\"");
                        quotesEnglish.add("\"It is not because it is hard that we do not dare to do it, but because we do not dare to do it, it is hard.\"");
                        quotesEnglish.add("\"I am not afraid of storms. I am learning how to steer my ship.\"");
                        quotesEnglish.add("\"The successful person learns from his mistakes and will start over on new paths.\"");
                        quotesEnglish.add("\"Don't look for mistakes, look for solutions.\"");
                        quotesEnglish.add("\"The solution is always simple, you just have to find it.\"");
                        quotesEnglish.add("\"All things are hard before they become easy.\"");
                        quotesEnglish.add("\"It's easy to learn from defeats. It is more difficult to learn from victories.\"");
                        quotesEnglish.add("\"A head without memory is a fortress without a garrison.\"");
                        quotesEnglish.add("\"A truth can only work when the recipient is ripe for it.\"");
                        quotesEnglish.add("\"Have courage to use your own mind.\"");
                        quotesEnglish.add("\"It is two different things to have received understanding and to have learned to use the understanding you have received.\"");
                        quotesEnglish.add("\"Especially people who do not learn anything, do not learn anything from the fact that they have not learned anything.\"");
                        quotesEnglish.add("\"Man is supposed to learn, only the ox crams.\"");
                        quotesEnglish.add("\"Repetition is the mother - not only of study, but also of education.\"");
                        quotesEnglish.add("\"Education is what remains when we forget what we have learned.\"");
                        quotesEnglish.add("\"Tell me, and I will forget. Show it to me, and I may keep it. Let me do it and I will be able to.\"");
                        quotesEnglish.add("\"If you don't try, you'll never know if you can.\"");
                        quotesEnglish.add("\"I don't have any special talent, I'm just passionately curious.\"");
                        quotesEnglish.add("\"Curiosity always comes first in a problem that wants to be solved.\"");
                        quotesEnglish.add("\"Enthusiasm is fertilizer for the brain.\"");
                        quotesEnglish.add("\"The brain is an organ with which we think that we think.\"");
                        quotesEnglish.add("\"The head is round so that thinking can change direction.\"");
                        quotesEnglish.add("\"Those who has straw in their heads fear the spark of truth.\"");
                        quotesEnglish.add("\"As you have sown, so shall you real.\"");
                        quotesEnglish.add("\"You have to have learned a lot to be able to ask about what you don't know.\"");
                        quotesEnglish.add("\"The greatest enemy of knowledge is not ignorance, but the illusion of being knowledgeable.\"");
                        quotesEnglish.add("\"We can learn the most from what we do not yet know.\"");
                        quotesEnglish.add("\"Live as if you would die tomorrow. Learn as if you would live forever.\"");
                        quotesEnglish.add("\"Education is something wonderful. But it should be remembered from time to time that what is truly worth knowing cannot be taught.\"");
                        quotesEnglish.add("\"Learn for your life, but don't forget to live.\"");
                        quotesEnglish.add("\"In three words I can sum up everything I've learned about life: it goes on.\"");
                        quotesEnglish.add("\"Detours also broaden our horizons.\"");
                        quotesEnglish.add("\"If you want to succeed, you have to learn that failures are healthy, inevitable steps on the way to the top.\"");

                        // Button contents [IMPORTANT:IF YOU ADD SMK ;THINK ABOUT textroulette to
                        // update]
                        btContent.add(i18n.get("BtQuotes1"));
                        btContent.add(i18n.get("BtQuotes2"));
                        btContent.add(i18n.get("BtQuotes3"));

                        // Layout des aufgehenden Fensters
                        Stage stage = new Stage();
                        VBox layout = new VBox();
                        Scene scene = new Scene(layout);



                       // Text = new Quotes -> Check if i18n German or English

                        Text Zitat = new Text((i18n.getLocale() == Locale.GERMAN) ? quotesGerman.get(randomNumber) : quotesEnglish.get(randomNumber));
                        Button checkout = new Button(btContent.get(BtRandom));

                        layout.getChildren().addAll(Zitat, checkout);

                        stage.setScene(scene);
                        stage.setTitle(i18n.get("StTitleQuotes"));
                        stage.setWidth(800);
                        stage.setHeight(100);
                        stage.show();
                        checkout.setOnAction(action -> stage.close());

                        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());

        }


}
