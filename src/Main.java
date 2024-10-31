import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Compress a file");
            System.out.println("2. Decompress a file");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the path of the input file to compress: ");
                    String inputFilePath = scanner.nextLine();
                    System.out.print("Enter the path for the output compressed file: ");
                    String outputFilePath = scanner.nextLine();
                    Compressor.compress(inputFilePath, outputFilePath);
                    System.out.println("File compressed successfully!");
                    break;
                case 2:
                    System.out.print("Enter the path of the input compressed file to decompress: ");
                    String compressedFilePath = scanner.nextLine();
                    System.out.print("Enter the path for the output decompressed file: ");
                    String decompressedFilePath = scanner.nextLine();
                    Decompressor.decompress(compressedFilePath, decompressedFilePath);
                    System.out.println("File decompressed successfully!");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
            System.out.println();
        } while (choice != 3);

        scanner.close();
    }
}
