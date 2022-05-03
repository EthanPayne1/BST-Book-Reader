import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class WordFrequencyCounter {
   public static void main(String[] args) {
    // Instantiate a new BST with type Item default copy constructer 
    BST<Item> tree = new BST<Item>();
    VisitItem vItem = new VisitItem();
    String itemWord =  "";
   
    // Creates a variable fileIn from class Scanner and assigns it to null 
    Scanner fileIn;
    try { // Constructs a new Scanner that produces values scanned from the specified string
        fileIn = new Scanner(readInData("CosmicComputer.txt")); 
        while (fileIn.hasNext()) {
            itemWord = fileIn.nextLine(); // Assigns itemWord to the lext 
            itemWord = itemWord.toLowerCase();
            itemWord = itemWord.replaceAll("\\p{Punct}", "");
            
            String words[] = itemWord.split(" "); // After the split create a temporary item and try to insert 
            String word;
            // Builing the tree
            for (int i = 0; i < words.length; i++) {
                Item newItem = new Item(words[i]);
                word = words[i];
                if (word.length() == 0) continue;
                if (Character.isDigit(word.charAt(0))) continue;
                
                if (tree.search(newItem) == null) 
                    tree.insert(newItem);
                else 
                   tree.search(newItem).incrementCount();
            }
        }
    }
    catch (Exception e) {
        System.out.println("The code is reaching the catch");
        e.printStackTrace();
        System.exit(1);
    }
    // Output the data
    System.out.println("Number of unique words: " + tree.size());
    tree.inorderTraverse(vItem);
    System.out.println("Most frequent word: " + vItem.getMaxWord() + " @ " + vItem.getMaxCount());
}
   static String readInData(String fileName) {
        Path filePath = Path.of(fileName);
        String fileContents = "";
        try {
            fileContents = Files.readString(filePath);
        }
        catch (IOException e) {
            System.out.println("Exception thrown: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return fileContents;
    }
}

class Item implements Comparable<Item> {

    private String word;
    private int count;

    public Item(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public Item(String word) {
        this(word, 1);
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }

    @Override
    public String toString() {
        return word + ": " + count;
    }

    @Override
    public int compareTo(Item o) {
        return word.compareTo(o.getWord());
    }
}

class VisitItem implements IVisit<Item> {
    private int count = 0;
    private String maxWord = "";
    private int maxCount = 0;

    @Override
    public void visit(Item data) {

        if (data.getCount() > maxCount) {
            maxCount = data.getCount();
            maxWord = data.getWord();
        }
        if (count < 20) {
            System.out.println(data);
            count++;
        }
    }

    public String getMaxWord() {
        return maxWord;
    }

    public int getMaxCount() {
        return maxCount;
    }
}