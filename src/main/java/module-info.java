module com.calendarfx.app {
    requires transitive javafx.graphics;

    requires javafx.controls;
    requires com.calendarfx.view;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;

    exports Studyplaner;
    exports DataAccess;
    exports Model;
    exports Helper;
    exports View;
    
    opens Model;
    opens Helper;
}