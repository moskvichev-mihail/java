import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    try {
      if (args.length != 2) {
        System.out.println("Incorrect number of arguments");
        return;
      }

      FileReader iStream = new FileReader(args[0]);
      ArrayList<String> priorityList = new ArrayList<>();
      Map<String, Integer> storage = processFile(iStream, priorityList);
      printWords(storage, Integer.valueOf(args[1]));
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  private static Map<String, Integer> processFile(FileReader iStream, ArrayList<String> priorityList) throws IOException {
    BufferedReader reader = new BufferedReader(iStream);
    Map<String, Integer> storage = new HashMap<>();

    String line = reader.readLine();
    while (line != null) {
      String[] lineArr = line.split(" ");
      processLine(lineArr, storage, priorityList);
      line = reader.readLine();
    }

    return storage;
  }

  private static void processLine(String[] line, Map<String, Integer> storage, ArrayList<String> priorityList)
  {
    for (String item : line) {
      if (storage.containsKey(item)) {
        storage.put(item, storage.get(item) + 1);
        int index = priorityList.indexOf(item);
        if (index != (priorityList.size() - 1)) {
          priorityList.remove(item);
          priorityList.add(index + 1, item);
        }
      } else {
        storage.put(item, 1);
        priorityList.add(0, item);
      }
    }
  }

  private static void printWords(Map<String, Integer> orderedMap, int countWords) {
    orderedMap.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(countWords)
            .forEach(System.out::println);
  }
}
