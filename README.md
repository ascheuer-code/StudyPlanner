<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>


<body>
    <div class="container">
        <button class="button1"> &equiv;
            <table>
                <tr>
                    <td><a href="#content">Table of Content</a></td>
                </tr>
                <tr>
                    <td>
                        <a href="#About"> About the Study Planer</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#how_to_add"> How to add a Modul</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#how_to_delete">How to delete a Modul</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#how_to_build">How to Build a Session</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#how_to_delete_lecture">How to How to delete a lecture</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#Experimental Feature">Automatic learning plan generation</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#export_events">Export Calendar Entries</a>
                    </td>
                </tr>
            </table>
        </button>
    </div>
    <h1>Studyplaner</h1>
    <h3>The user manual for the Study Planer</h3>
    <p>This app was developed to help students better plan their studies and to better assess the effort required for
        the
        modules they take.</p>
    <h2 id="content">Contents</h2>
    <h3>1. About the Study Planer</h3>
    <h3>2. Moduls:</h3>
    <p>- How to add a Modul</p>
    <p>- How to edit a Modul</p>
    <p>- How to delete a Modul</p>
    <h3>3. How to build a timetable / learning session:</h3>
    <p>- How to add a lecture / learning session</p>
    <p>- How to delete a lecture / learning session</p>
    <p>- How to use the Automatic learning plan generator</p>
    <h3>4. How to Export all Calendar Events</h3>
    <p>- How to Export your Events</p>
    <h2 id="About">1. About the Study Planer</h2>
    <h3>This is how the calendar looks like:</h3>
    <table>
        <tr>
            <td>
                <p>You see your Moduls on the left side and the calendar on the right side. to see the week click on
                    week
                    on the
                    middle Top:</p>
                <img src="./images/CalenderOverview.png" alt="CalenderOverview">
            </td>
        </tr>
        <tr>
            <td>
                <p>for a monthly overview click on month</p>
                <img src="./images/MonthView.png" alt="MonthView">
            </td>
        </tr>
        <tr>
            <td>
                <p>Or if you want to see the whole year click on year:</p>
                <img src="./images/YearView.png" alt="YearView">
            </td>
        </tr>
        <tr>
            <td>
                <p>you can change the Language via clicking on the Buttons on the lower corner</p>
                <img src="./images/language.png" alt="language">
            </td>
        </tr>
    </table>
    <h2 id="how_to_add">How to add a Modul</h2>
    <h3>First Step:</h3>
    <p>You click on the button Modul anlegen to add a new Modul</p>
    <h3> Second Step:</h3>
    <table>
        <tr>
            <td>
                <p>
                    When you clicked on the Modul anlegen button a window will open in the middle of your Screen.
                </p>
                <p>
                    here you can set a Modul name and the Credit Points for this modul. Then you can click on the
                    save button.
                </p>
                <img src="./images/addModulWindow.png" alt="addModulWindow">
            </td>
        </tr>
        <tr>
            <td>
                <p>This is how it looks like when you added a Modul: </p>
                <img src="./images/Moduladded.png" alt="Moduladded">
            </td>
        </tr>
    </table>
    <p>
        You have to click on the modul you want to change. Then you can set up your changes and click on the change
        button.
    </p>
    <h2 id="how_to_delete">
        How to delete a Modul
    </h2>
    <table>
        <tr>
            <td>
                <p>
                    When you want to delete a modul you have to click on the Modul delete button. Select the modul you
                    want to
                    delete and
                    put a hack into the check box are you sure you want to delete this modul.
                </p>
                <img src="./images/DeleteModul.png" alt="DeleteModul">
            </td>
        </tr>
    </table>
    <h2 id="how_to_build">
        3. How to build a timetable/ learning session
    </h2>
    <p>
        How to add a lecture/ learning session
    </p>
    <p>
        First step:
    </p>
    <p>
        You can add a lecture to your timetable while you click on the Erstellen eines Events button.
    </p>
    <p>
        Second step:
    </p>
    <p>
        A window will open where you have to choose witch Modul you want to add to your timetable. Then you have to
        choose if
        this event is a part of your learning session or your timetable. You also have to choose the starttime and the
        endtime
        and the date.
        If you wish, you can choose a repetition cycle of 1 2 3 4 5 6 7 14 days unter Repetition in days, but
        don't
        forget to indicate until when the cycle should end in the field below.
    </p>
    <img src="./images/Eventadded.png" alt="Eventadded">
    <p>
        The following fields are mandatory: Modulname, calendar, Date, Start Time, End Time, otherwise will be no
        lecture added.
    </p>
    <p>
        Optionally you can add a description to the event.
        --> The credit points of a Modul will automatically convert into hours, and always when you add a lecture or a
        learning
        session the time you invest in a modul will automatically decrease
    </p>
    <h2 id="how_to_delete_lecture">
        How to delete a lecture / learning session
    </h2>
    <p>
        To delete a lecture or a learning session you have to right-click on the entry in your calendar you want to
        delete. Then
        choose to delete or by selecting the event by left click and press delete on your Keyboard.
    </p>
    <img src="./images/deleteEntry.png" alt="deleteEntry">
    <h2 id="Experimental Feature">
        Experimental Feature
    </h2>
    <h2>
        Automatic learning plan generation
    </h2>
    <p>
        Via the button "Create filler event" you can create so-called filler events, which are then assigned to the
        available
        modules via the button "Create learning plan".
    </p>
    <img src="./images/createFiller.png" alt="createFiller">
    <p>Please note that this function is still in an experimental phase.</p>
    <h2 id="export_events">
        4. How to Export all Calendar Events
    </h2>
    <p>
        You can export your calendar entries by pressing the calendar export button.
    </p>
    <p>
        you can find the file in the same directory as the Studyplanner.jar under the name Studyplanner.ics
    </p>
    <img src="./images/exportEvent.png" alt="exportEvent">
</body>
<footer>
</footer>

</html>