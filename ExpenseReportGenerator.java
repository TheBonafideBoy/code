import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This file is used to generate the summary of expenditures for each
 * category from the csv generated by the splitwise Mobile Application.
 * User is expected to enter the name of the csv file through command line.
 * 
 * @author Vijay Kumar
 */

public class ExpenseReportGenerator {
    public static void main(String[] args) {
        try {
            String line = null;
            Map<String, Double> categoryCostMap = new HashMap<>();
            BufferedReader inputFile = new BufferedReader(new FileReader(args[0].trim()));
            BufferedWriter outputFile = new BufferedWriter(new FileWriter("ExpenseReport.txt"));
            
            // Ignore first two lines as these are headers and blank lines.
            inputFile.readLine();
            inputFile.readLine();

            while ((line = inputFile.readLine()) != null) {
                String[] values = line.split(",");
                // Last line is empty
                if (values.length == 1) break;

                String category = values[1].trim();
                double cost = Double.parseDouble(values[3]);
                categoryCostMap.put(category, categoryCostMap.getOrDefault(category, 0.0) + cost);
            }

            double totalExpenses = 0.0;
            for (String category : categoryCostMap.keySet()) {
                double value = categoryCostMap.get(category);
                totalExpenses += value;
                String output = String.format("%-25s%-5.2f\n", category, value);
                outputFile.write(output);
            }
            outputFile.write("\n");
            outputFile.write(String.format("%-25s%-5.2f\n", "Total Expenditures", totalExpenses));

            inputFile.close();
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

Extra lines