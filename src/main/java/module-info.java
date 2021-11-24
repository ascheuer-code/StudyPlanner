module com.calendarfx.app {
    requires transitive javafx.graphics;

    requires javafx.controls;
    requires com.calendarfx.view;

    exports com.studyplanner.code_ascheuer.github;
    exports Model;
    exports View;
}