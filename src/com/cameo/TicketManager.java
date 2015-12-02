package com.cameo;
import java.util.Date;
import java.util.Scanner;
import java.util.LinkedList;

public class TicketManager {

    public static void main(String[] args) {
        LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();

        Scanner sc = new Scanner(System.in);

        //Ask for some ticket info, create tickets, store in ticketQueue
        while (true) {
            System.out.println("1. Enter Ticket\n2. Delete Ticket by ID\n3. Delete Ticket by Issue\n" +
                    "4. Delete Ticket by Name\n5. Display All Tickets\n6. Quit");
            int task = Integer.parseInt(sc.nextLine());
            if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);
            } else if (task == 2) {
                //delete a ticket by ID
                printAllTickets(ticketQueue); //display list for user
                deleteTicketbyID(ticketQueue);
            } else if (task == 3) {
                //delete a ticket by Issue
                printAllTickets(ticketQueue);   //display list for user
                deleteTicketbyString(ticketQueue, "issue");
            } else if (task == 4) {
                //delete a ticket by name
                printAllTickets(ticketQueue);   //display list for user
                deleteTicketbyString(ticketQueue, "name");
            } else if (task == 6) {
                //Quit. Future prototype may want to save all tickets to a file
                System.out.println("Quitting program");
                break;
            } else {
                //this will happen for 5 or any other selection that is a valid int
                //TODO Program crashes if you enter anything else - please fix
                //Default will be print all tickets
                printAllTickets(ticketQueue);
            }
        }
        sc.close();
    }

    private static void deleteTicketbyID(LinkedList<Ticket> ticketQueue) {
        Scanner a = new Scanner(System.in);
        //received help from Matt Rowe on this while loop
        boolean ticketNotFound = true;
        while (ticketNotFound) {
            System.out.println("Enter ID of ticket to delete");
            int deleteID;
            try {
                deleteID = a.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            //Loop over all tickets. Delete the one with this ticket ID
            boolean found = false;
            for (Ticket ticket : ticketQueue) {
                if (ticket.getTicketID() == deleteID) {
                    found = true;
                    ticketQueue.remove(ticket);
                    System.out.println(String.format("Ticket %d deleted", deleteID));
                    ticketNotFound = false;
                    break; //don't need loop any more.
                }
            }

            if (found == false) {
                System.out.println("Ticket ID not found, no ticket deleted");
            }
            printAllTickets(ticketQueue);  //print updated list
        }
    }

    private static void deleteTicketbyString(LinkedList<Ticket> ticketQueue, String deleteByChoice) {
        if (ticketQueue.size() == 0) {    //no tickets!
            System.out.println("No tickets to delete!\n");
            return;
        }
        LinkedList<Ticket> listOfTicketsMatchingString = new LinkedList<>();
        Scanner a = new Scanner(System.in);
        boolean ticketNotFound = true;
        String response = "";

        while (ticketNotFound) {
            if (deleteByChoice.equals("name")) {
                System.out.println("Enter name of the person who reported the ticket you would like to delete");
                response = a.nextLine();
                //Loop over all tickets. Create a new LinkedList of tickets to delete
                for (Ticket ticket : ticketQueue) {
                    if (ticket.getReporter().contains(response)) {
                        listOfTicketsMatchingString.add(ticket);
                    }
                }
            }
            else if (deleteByChoice.equals("issue")){
                System.out.println("Enter a brief description of the issue of the ticket you would like to delete");
                response = a.nextLine();
                //Loop over all tickets. Create a new LinkedList of tickets to delete
                for (Ticket ticket : ticketQueue) {
                    if (ticket.getDescription().contains(response)) {
                        listOfTicketsMatchingString.add(ticket);
                    }
                }
            }
            //if no tickets with the description are found
            if (listOfTicketsMatchingString.size() == 0) {
                System.out.println("There are no tickets matching your search request");
                break;
            } else {
                System.out.println("Here is the list of tickets matching your search request");
                for (Ticket ticket : listOfTicketsMatchingString){
                    System.out.println(ticket.toString());
                }
                //TODO This method only removes the ticket from listOfTicketsMatchingString, not from the general ticketQueue
                deleteTicketbyID(listOfTicketsMatchingString);
            }
            ticketNotFound = false;
        }
    }

    //Move the adding ticket code to a method
    protected static void addTickets(LinkedList<Ticket> ticketQueue) {
        Scanner sc = new Scanner(System.in);
        boolean moreProblems = true;
        String description;
        String reporter;
        //let's assume all tickets are created today, for testing. We can change this later if needed
        Date dateReported = new Date(); //Default constructor creates date with current date/time
        int priority;
        while (moreProblems){
            System.out.println("Enter problem");
            description = sc.nextLine();
            System.out.println("Who reported this issue?");
            reporter = sc.nextLine();
            System.out.println("Enter priority of " + description);
            priority = Integer.parseInt(sc.nextLine());
            Ticket t = new Ticket(description, priority, reporter, dateReported);
            ticketQueue.add(t);
            //To test, let's print out all of the currently stored tickets
            printAllTickets(ticketQueue);
            System.out.println("More tickets to add?");
            String more = sc.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }

    protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println(" ------- All open tickets ----------");
        for (Ticket t : tickets ) {
            System.out.println(t);
        }
        System.out.println(" ------- End of ticket list ----------");
    }
}