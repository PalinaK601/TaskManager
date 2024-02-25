package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) {
        String[][] tasks = wczytajTaskiZPliku();
        wywolujemyOpcje(tasks);
    }


    static String[][] wczytajTaskiZPliku() {
        String[][] tasks = new String[0][];
        File file = new File("tasks.csv");
        Scanner scan = null;
        try {
            scan = new Scanner(file);
            int iloscWierszy = 0;
            while (scan.hasNextLine()) {
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                String linia = scan.nextLine();
                tasks[iloscWierszy] = linia.split(",");
                iloscWierszy++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (scan != null) {

                scan.close();
            }
        }
        return tasks;
    }

    public static void wywolujemyOpcje(String[][] tasks) {
        String opcja = "";

        showOptions();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            opcja = scanner.nextLine();
            if (opcja.equals("list")) {
                list(tasks);
            } else if (opcja.equals("add")) {
                tasks = add(tasks, scanner);
            } else if (opcja.equals("remove")) {
                remove(tasks, scanner);
            } else if (opcja.equals("exit")) {
                exit(tasks);
            }
        }
    }

    private static void list(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            String[] wierszZTaskiem = tasks[i];
            String linia = StringUtils.join(wierszZTaskiem);
            System.out.println(i + ":" + linia);
        }
    }

    private static String[][] add(String[][] tasks, Scanner scanner) {
        System.out.println("Please add task description");
        String desc = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueData = scanner.nextLine();
        System.out.println("Is your task is important : true/false");
        Boolean impIs = scanner.nextBoolean();
        String[] zadania = new String[3];
        zadania[0] = desc;
        zadania[1] = dueData;
        zadania[2] = impIs.toString();
        int size = tasks.length + 1;
        tasks = Arrays.copyOf(tasks, size);
        tasks[size - 1] = zadania;
        System.out.println("zadanie zostalo dodane");
        showOptions();
        return tasks;
    }

    private static void exit(String[][] tasks) {
        try (PrintWriter out = new PrintWriter("tasks2.csv")) {
            for (String[] zadanie : tasks) {
                String linia = StringUtils.join(zadanie);
                out.println(linia);
            }
            System.out.println((" Your tasks were succesfully saved"));
            System.out.println(ConsoleColors.RED + "Bye, bye");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println(" some problem with your file");
        }
    }

    public static void showOptions() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");

    }

    public static void remove(String[][] tasks, Scanner scanner) {
        System.out.println((" Please select number to remove"));
        try {
            int number = scanner.nextInt();
            tasks = ArrayUtils.remove(tasks, number);
            System.out.println("Value was succesfully deleted");
        } catch (InputMismatchException e) {
            System.out.println("Incorrect argument passed. Please give a number greater or equal 0");
            remove(tasks, scanner);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect argument passed. Please give a number greater or equal 0 and lower than " + tasks.length);
            remove(tasks, scanner);
        }


    }
}




