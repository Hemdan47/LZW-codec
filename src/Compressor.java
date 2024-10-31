import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Compressor class provides functionality to compress text files using a simple dictionary-based approach.
 * It reads input from a file, compresses the content, and writes the compressed output to another file.
 */
public class Compressor {

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
     * Compresses the content of the input file and writes the compressed output to the output file.
     * The compression is achieved using a dictionary-based algorithm.
     *
     * @param inputFilePath  the path of the input file to compress
     * @param outputFilePath the path of the output file to write the compressed data
     */
    public static void compress(String inputFilePath , String outputFilePath) {
        String input = fileReader(inputFilePath); // Read the content of the input file
        Map<String , Integer> map = new HashMap<>();
        for(int i = 0 ; i <= 127 ; i++) {
            map.put(String.valueOf((char)i) , i); // Populate the map with initial character values
        }
        int lastIdx = 128; // Start index for new entries in the dictionary
        StringBuilder output = new StringBuilder(); // StringBuilder to hold the output
        StringBuilder sequence = new StringBuilder(); // StringBuilder to build sequences of characters

        for(int i = 0 ; i < input.length() ; i++) {
            // If the current sequence plus the next character is in the map
            if(map.containsKey(sequence.toString() + input.charAt(i))) {
                sequence.append(input.charAt(i)); // Add the character to the current sequence
            } else {
                // If not, write the index of the current sequence to the output
                output.append(map.get(sequence.toString()) + "\n");
                // Add the new sequence to the map and increment the last index
                map.put(sequence.toString() + input.charAt(i) , lastIdx++);
                i--; // Decrement i to reprocess the current character
                sequence.setLength(0); // Reset the sequence for the next iteration
            }
        }
        // If there is any remaining sequence, write it to the output
        if(!sequence.isEmpty()) {
            output.append(map.get(sequence.toString()) + "\n");
        }

        fileWriter(output.toString() , outputFilePath); // Write the compressed output to the file
    }
}
