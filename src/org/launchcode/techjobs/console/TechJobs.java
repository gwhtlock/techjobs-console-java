package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    System.out.println("Currently searching all columns.");

                    findByValue(searchTerm);

//                    ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
//
//                    jobs = JobData.findAll();
//
//                    printJobs(jobs);
                } else {
                    System.out.println("Currently searching: "+searchField+" column");

                    ArrayList<HashMap<String, String>> jobSearchResults = new ArrayList<>();
                    jobSearchResults = JobData.findByColumnAndValue(searchField, searchTerm);

                    if(jobSearchResults.isEmpty()){
                        System.out.println("Sorry!!! Jobs not found \nNo jobs associated with search term: "+ searchTerm.toUpperCase()+" Have been located");
                    }else {

                        printJobs(jobSearchResults);
//                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
//                    System.out.println(searchField);
//                    System.out.println(searchTerm);
//
//                    ArrayList<HashMap<String, String>> jobs = new ArrayList<>();  //created another arraylsit to catch results of JobData.findByColumnAndValue(searchField, searchTerm) ot currenty returns an empyt arraylist

//                    if(jobs.isEmpty()){
//                        System.out.println("array list retrieved from search is empty");
//                    }else{
//                        System.out.println("arraylist search results below");
//                        System.out.println(jobs);
//                    }
                    }
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
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

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

//        someJobs.clear(); //clears current arraylist to test error message in first if statement

        if(someJobs.isEmpty()){
            System.out.println("No jobs currently listed");
        } else {

            for (HashMap<String, String> jobs : someJobs) {
                System.out.println("*******************************************");
                for (Map.Entry<String, String> job : jobs.entrySet()) {
                    System.out.println(job.getKey() + ":  " + job.getValue());
                }
                System.out.println("*******************************************");
                System.out.println("");
                System.out.println("");

            }
        }





    }

    public static void findByValue(String searchterm){

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
        ArrayList<HashMap<String, String>> jobsFound = new ArrayList<>();

        jobs = JobData.findAll();

        for(HashMap<String,String> job : jobs){ // iterates through the arraylist of hashkmaps setting job to the value of each hashmap within the arraylist in turn


            for(String key : job.values()){

                //makes the
               key = key.toLowerCase();
               searchterm = searchterm.toLowerCase();



                if(key.contains(searchterm)){
                    jobsFound.add(job);
                }
            }

//            if (job.containsValue(searchterm)){
//                jobsFound.add(job);
//            }

        }

        if(jobsFound.isEmpty()){
            System.out.println("Sorry!!! Jobs not found \nNo jobs associated with search term: "+searchterm.toUpperCase()+" Have been located");
        }else {

            printJobs(jobsFound);

        }



    }
}
