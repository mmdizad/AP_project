package View;

import Controller.ImportAndExportController;

import java.util.Scanner;
import java.util.regex.Matcher;


public class ImportAndExport extends MainMenu {
    private static ImportAndExport importAndExport = new ImportAndExport();
    private boolean isCommandInvalid = true;

    private ImportAndExport() {

    }

    public static ImportAndExport getInstance() {
        return importAndExport;
    }

    public void run(Scanner scanner) {
        while (true){
            String command = scanner.nextLine();
            importCard(getCommandMatcher(command,"^import card (.+)$"));
            exportCard(getCommandMatcher(command,"^export card (.+)$"));
            if (isCommandInvalid){
                System.out.println("invalid command");
            }
            isCommandInvalid = true;
            if (command.equals("menu exit")){
                return;
            }
        }
    }

    public void importCard(Matcher matcher) {
        if (matcher.find()){
            isCommandInvalid = false;
            ImportAndExportController importAndExportController = ImportAndExportController.getInstance();
            System.out.println(importAndExportController.importController(matcher));
        }
    }

    public void exportCard(Matcher matcher) {
        if (matcher.find()){
            isCommandInvalid = false;
            ImportAndExportController importAndExportController = ImportAndExportController.getInstance();
            System.out.println(importAndExportController.exportController(matcher));
        }
    }

}