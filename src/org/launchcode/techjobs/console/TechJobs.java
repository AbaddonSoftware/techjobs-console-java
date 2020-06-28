package org.launchcode.techjobs.console;

import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {
        String searchTerm;
        boolean searching = true;

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
                    results.forEach(System.out::println);
                    break;
                case "search all":
                    searchTerm = queryUser("\nSearch Term: ");
                    printJobs(JobData.findByValue(searchTerm));
                    break;
                case "search by category":
                    String searchField = getUserSelection("Search by:", columnChoices);
                    searchTerm = queryUser("\nSearch Term: ");
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

    private static String queryUser(String query) {
        System.out.println(query);
        return in.nextLine();
    }
    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, LinkedHashMap<String, String> choices) {
        int choiceIdx = -1;
        boolean validChoice;
        String[] choiceKeys = new String[choices.size()];


        do {
            System.out.println("\n" + menuHeader);

            for (int optionNumber = 0; optionNumber < choiceKeys.length; optionNumber++) {
                choiceKeys[optionNumber] = (String) choices.keySet().toArray()[optionNumber];
                System.out.println("" + optionNumber + " - " + choices.get(choiceKeys[optionNumber]));
            }

            try {
                choiceIdx = in.nextInt();
            }
            catch (InputMismatchException error) {
                System.out.println(error.toString()+ " occurred but no need to let that ruin our day... maybe.");
            }

            in.nextLine();
            validChoice = choiceIdx >= 0 && choiceIdx < choices.size();
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
                resultMap.forEach((key, value) -> System.out.println(key + ": " + value));
                System.out.println("*****\n");
            }
        }
    }
}
