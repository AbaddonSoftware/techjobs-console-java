package org.launchcode.techjobs.console;

import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {
        String searchTerm;
        Boolean searching = true;

        // Initialize our field map with key/name pairs
        LinkedHashMap<String, String> columnChoices = new LinkedHashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
//        columnChoices.put("all", "All");

        // Top-level menu options
        LinkedHashMap<String, String> actionChoices = new LinkedHashMap<>();
        actionChoices.put("search all", "Search All");
        actionChoices.put("search by category", "Search By Category");
        actionChoices.put("list all", "List All");
        actionChoices.put("list by category", "List By Category");
        actionChoices.put("exit the system", "Exit The System");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (searching) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            switch(actionChoice) {
                case "list all":
                    printJobs(JobData.findAll());
                    break;
                case "list by category":
                    String columnChoice = getUserSelection("List", columnChoices);
                    ArrayList<String> results = JobData.findAll(columnChoice);
                    results.sort(Comparator.comparing(String::toLowerCase));
                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");
                    // Print list of skills, employers, etc
                    results.forEach(item -> { System.out.println(item); } );
                    break;
                case "search all":
                    searchTerm = getUserSelection("\nSearch Term: ");
                    printJobs(JobData.findByValue(searchTerm));
                    break;
                case "search by category":
                    String searchField = getUserSelection("Search by:", columnChoices);
                    searchTerm = getUserSelection("\nSearch Term: ");
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                    break;
                case "exit the system":
                    searching = false;
                    break;
                default:
                    System.out.println("Feature not yet implemented");
                    break;
            }
        }
    }

    private static String getUserSelection(String query) {
        System.out.println(query);
        String userResponse = in.nextLine();
        return userResponse;
    }
    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, LinkedHashMap<String, String> choices) {
        Integer choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            try {
                choiceIdx = in.nextInt();
            }
            catch (InputMismatchException error) {
                System.out.println(error.toString()+ " occurred but no need to let that ruin our day.");
            }

            in.nextLine();
            // Validate user's input
            validChoice = choiceIdx >= 0 && choiceIdx < choiceKeys.length;
            if(!validChoice) {
                System.out.println("Invalid choice, Try again.");
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        if (someJobs.isEmpty())
        {
            System.out.println("No results found.");
        }
        else
        {
            for (HashMap<String, String> resultMap : someJobs) {
                System.out.println("*****");
                resultMap.entrySet().forEach(
                        element->{ System.out.println(element.getKey()+ ": " + element.getValue()); });
                System.out.println("*****\n");
            }
        }
    }
}
