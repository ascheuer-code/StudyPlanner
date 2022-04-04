package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * the class ICalender
 */
public class ICalender {

    /**
     * method iCalenderExport, to export the calender
     * @param eventList
     * @param filename
     * @throws IOException
     */
    public void iCalenderExport(List<Event> eventList, String filename) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("BEGIN:VCALENDAR\n" +
                "PRODID:-//Studyplaner//EN\n" +
                "VERSION:2.0\n" +
                "CALSCALE:GREGORIAN\n" +
                "METHOD:PUBLISH\n" +
                "X-WR-CALNAME:Studyplaner\n" +
                "X-WR-TIMEZONE:Europe/Berlin\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID:Etc/UTC\n" +
                "X-LIC-LOCATION:Etc/UTC\n" +
                "BEGIN:STANDARD\n" +
                "TZOFFSETFROM:+0000\n" +
                "TZOFFSETTO:+0000\n" +
                "TZNAME:GMT\n" +
                "DTSTART:19700101T000000\n" +
                "END:STANDARD\n" +
                "END:VTIMEZONE\n");


        Writer fw = new FileWriter(filename,false);
        for (Event event:eventList) {
            sb.append("BEGIN:VEVENT\n" +
                    "DTSTART:"+ convertDate(event.getStarDate()) + "T" +   convertTime(LocalTime.parse(event.getStartTime()).minusHours(1).toString())+"00Z\n" +
                    "DTEND:" + convertDate(event.getEndDate()) +   "T" +convertTime(LocalTime.parse(event.getEndTime()).minusHours(1).toString())+"00Z\n" +
                    "DTSTAMP:" + convertDate(LocalDate.now().toString()) +  "T" + convertLocalTime(convertTime(LocalTime.now().minusHours(1).toString()))+"Z\n" +
                    "UID:"+ event.getId() +"\n" +
                    "CREATED:"+ convertDate(LocalDate.now().toString()) +  "T" + convertLocalTime(convertTime(LocalTime.now().minusHours(1).toString()))+"Z\n" +
                    "DESCRIPTION:\n" +
                    "LAST-MODIFIED:"+ convertDate(LocalDate.now().toString()) +  "T" + convertLocalTime(convertTime(LocalTime.now().minusHours(1).toString()))+"Z\n" +
                    "LOCATION:\n" +
                    "SEQUENCE:0\n" +
                    "STATUS:CONFIRMED\n" +
                    "SUMMARY:"+ convertTitle(event.getTitle()) + "\n" +
                    "TRANSP:OPAQUE\n" +
                    "END:VEVENT\n");

        }
        sb.append("END:VCALENDAR");
        String result = sb.toString();
        fw.write(result);
        fw.close();
    }
    public String convertTime(String string){
        return string.replaceAll(":","");
    }
    public String convertDate(String string){
        return string.replaceAll("-","");
    }
    public String convertLocalTime(String string){
        return string.replaceAll("\\.\\d*","");
    }
    public String convertTitle(String string){
        return string.replaceAll("\\n"," ");
    }
}
