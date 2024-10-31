import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Decompressor class provides functionality to decompress text files that were compressed
 * using the Compressor class. It reads compressed data from a file, reconstructs the original content,
 * and writes the decompressed output to another file.
 */
public class Decompressor {

    /**
     * Reads the content of a file and returns it as a String.
     *
     * @param filePath the path of the file to read
     * @return the content of the file as a String
     */
    private static String fileReader(String filePath) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Exception thrown  :" + e);
        }
        return text.toString();
    }

    /**
     * Writes the given output string to a specified file.
     *
     * @param output   the content to write to the file
     * @param filePath the path of the file to write to
     */
    private static void fileWriter(String output, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(output);
        } catch (IOException e) {
            System.out.println("Exception thrown  :" + e);
        }
    }

    /**
     * Decompresses the content of the input file and writes the decompressed output to the output file.
     * The decompression is achieved using a dictionary-based algorithm that reconstructs the original data.
     *
     * @param inputFilePath  the path of the input file containing compressed data
     * @param outputFilePath the path of the output file to write the decompressed data
     */
    public static void decompress(String inputFilePath , String outputFilePath) {
        String input = fileReader(inputFilePath); // Read the content of the input file
        Map<Integer , String> map = new HashMap<>();
        for(int i = 0 ; i <= 127 ; i++) {
            map.put(i , String.valueOf((char)i)); // Populate the map with initial character values
        }
        int lastIdx = 128; // Start index for new entries in the dictionary
        StringBuilder output = new StringBuilder(); // StringBuilder to hold the output
        String[] inputArray = input.split("\n"); // Split the input into an array by line

        output.append(map.get(Integer.parseInt(inputArray[0]))); // Append the first entry to output
        for (int i = 1; i < inputArray.length; i++) {
            // Get the previous map entry and construct the current entry
            String mapEntry = map.get(Integer.parseInt(inputArray[i-1]));
            mapEntry += (map.containsKey(Integer.parseInt(inputArray[i])) ?
                    map.get(Integer.parseInt(inputArray[i])).charAt(0) :
                    mapEntry.charAt(0));
            map.put(lastIdx++ , mapEntry); // Add the new entry to the map
            output.append(map.get(Integer.parseInt(inputArray[i]))); // Append the current entry to output
        }

        output.deleteCharAt(output.length()-1); // Remove the last newline character from output

        fileWriter(output.toString() , outputFilePath); // Write the decompressed output to the file
    }
}
