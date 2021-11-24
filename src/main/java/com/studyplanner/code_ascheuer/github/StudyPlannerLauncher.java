package com.studyplanner.code_ascheuer.github;

/**
 * The type Study planner launcher.
 */
public class StudyPlannerLauncher {

    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.setProperty("calendarfx.developer", "true");
        StudyPlanner.main(args);
    }
}