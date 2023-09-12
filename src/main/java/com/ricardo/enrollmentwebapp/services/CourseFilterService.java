package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.entities.Major;
import com.ricardo.enrollmentwebapp.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CourseFilterService
{
    private final CourseRepository courseRepository;


    public static final String[] BEGINNER_ENGLISH_CODES = { "GEEN1101", "GEEN1102", "GEEN1103"} ;
    public static final String[] INTERMEDIATE_ENGLISH_CODES = { "GEEN1201", "GEEN1202", "GEEN1203"} ;
    public static final String[] ADVANCED_ENGLISH_CODES = { "GEEN2311", "GEEN2312", "GEEN2313"} ;
    public static final String[] APPRECIATION_CODES = { "GEPE3010", "GEPE3020", "GEPE3030"} ;
    public static final String[] HISTORY_CODES = { "GEHS3020", "GEHS3050", "GEHS4020", "GEHS4030"} ;


    /**
     * Checks if the approved courses contains any of the courses to filter,
     * and removes the unnecessary courses from the remaining courses list
     * @param approvedCourses The list of approved courses
     * @param remainingCourses The full unfiltered list of remaining courses
     * @param coursesToFilter The codes of the courses to filter from the list of remaining courses
     */
    public void filterCourses(List<Course> approvedCourses, List<Course> remainingCourses, String[] coursesToFilter)
    {
        List<Course> appreciationCourses = courseRepository.findAllById(List.of(coursesToFilter));

        for (Course appreciationCourse: appreciationCourses)
        {
            if (approvedCourses.contains(appreciationCourse))
            {
                appreciationCourses.remove(appreciationCourse);
                remainingCourses.removeAll(appreciationCourses);
            }
        }
    }

    public void filterEnglishCourses(List<Course> approvedCourses, List<Course> remainingCourses)
    {
        List<List<Course>> allEnglishCourses = new ArrayList<>();
        allEnglishCourses.add(courseRepository.findAllById(List.of(BEGINNER_ENGLISH_CODES)));
        allEnglishCourses.add(courseRepository.findAllById(List.of(INTERMEDIATE_ENGLISH_CODES)));
        allEnglishCourses.add(courseRepository.findAllById(List.of(ADVANCED_ENGLISH_CODES)));

        boolean hasTakenEnglish = false;
        int englishLevel = -1;
        for (int i = 0; i < allEnglishCourses.size(); i++)
        {
            for (Course englishCourse: allEnglishCourses.get(i))
            {
                if (approvedCourses.contains(englishCourse))
                {
                    hasTakenEnglish = true;
                    englishLevel = i;
                    break;
                }
            }
        }

        for (int i = 0; i < allEnglishCourses.size(); i++)
        {
            if (hasTakenEnglish && i != englishLevel)
            {
                remainingCourses.removeAll(allEnglishCourses.get(i));
            }
        }
    }

    public void filterDistributiveCourses(Major major, List<Course> approvedCourses, List<Course> remainingCourses)
    {
        int approvedCredits = 0;
        for (Course approvedCourse: approvedCourses)
        {
            approvedCredits += approvedCourse.getCreditCount();
        }

        if (approvedCredits >= major.getDistributiveCreditsRequirement())
            remainingCourses.clear();
    }
}
