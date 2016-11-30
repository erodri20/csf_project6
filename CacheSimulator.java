import java.io.*;

public class CacheSimulator {
    private static Cache cache;
    private static int total_loads;
    private static int total_stores;
    private static int load_hits;
    private static int load_misses;
    private static int store_hits;
    private static int store_misses;
    private static int cycles;
    private static int least_recently_used;
    private static int write_allocate;
    private static int write_through;

    public static void main(String[] args) {
      if(args.length != 7) {
        System.out.println("Error: Incorrect number of arguments");
      } else {
          //command-line args
          int num_sets = Integer.parseInt(args[0]);
          int num_blocks = Integer.parseInt(args[1]);
          int num_addresses = Integer.parseInt(args[2]);
          write_allocate = Integer.parseInt(args[3]);
          write_through = Integer.parseInt(args[4]);
          least_recently_used = Integer.parseInt(args[5]);
          String fileName = args[6];

          cache = new Cache (num_sets, num_blocks, num_addresses);
          String line = null;

          try {
              FileReader fileReader =
                  new FileReader(fileName);

              BufferedReader bufferedReader =
                  new BufferedReader(fileReader);

              //Cache Simulation
              while((line = bufferedReader.readLine()) != null) {
                  simulate(line);
              }

              bufferedReader.close();
          }
          catch(FileNotFoundException ex) {
              System.out.println(
                  "Unable to open file '" +
                  fileName + "'");
          }
          catch(IOException ex) {
              System.out.println(
                  "Error reading file '"
                  + fileName + "'");
          }
        }
    }

    public static void simulate(String line) {
        String[] arguments = line.split(" ");
        //Arguments[0] is store or load
        //Arguments[1] is memory address
        if (arguments.length >= 2) {
            if (arguments[0].equals("s")) {
              store(Integer.parseInt(arguments[1]));
              total_stores++;
            } else if (arguments[0].equals("l")) {
                load(Integer.parseInt(arguments[1]));
                total_loads++;
            } else {
                System.out.println("Error! First argument must be s or l.");
            }
        }
        else {
            System.out.println("Error! Too few arguments on line.");
        }
    }

    public static void load(int memory_address) {
      if(cache.search(memory_address).equals("hit")) {
        //handle hit
        load_hits++;
        cycles++;
      } else {
        //handle miss
        cycles += cache.handleLoadMiss(memory_address, least_recently_used);
        load_misses++;
      }

    }

    public static void store(int memory_address) {
      if(cache.search(memory_address).equals("hit")) {
        //handle hit
        store_hits++;
      } else {
        //handle miss
        store_misses++;
      }
    }
}
