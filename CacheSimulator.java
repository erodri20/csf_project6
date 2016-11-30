/*

Natasha Bornhorst - nbornho1@jhu.edu
Elise Rodrigues - erodri20@jhu.edu

*/
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

        System.out.println("Total loads: " + total_loads);
        System.out.println("Total stores: " + total_stores);
        System.out.println("Load hits: " + load_hits);
        System.out.println("Load misses: " + load_misses);
        System.out.println("Store hits: " + store_hits);
        System.out.println("Store misses: " + store_misses);
        System.out.println("Total cycles: " + cycles);
    }

    public static void simulate(String line) {
        String[] arguments = line.split(" ");
        //Arguments[0] is store or load
        //Arguments[1] is memory address
        if (arguments.length >= 2) {
            if (arguments[0].equals("s")) {
              store(arguments[1]);
              total_stores++;
            } else if (arguments[0].equals("l")) {
                load(arguments[1]);
                total_loads++;
            } else {
                System.out.println("Error! First argument must be s or l.");
            }

            System.out.println("BEGIN");
            cache.print();
            System.out.println("END");
            System.out.println();


        }
        else {
            System.out.println("Error! Too few arguments on line.");
        }
    }

    public static void load(String memory_address) {
      if(cache.search(memory_address).equals("hit")) {
        //handle hit
        load_hits++;
        cycles++;
      } else {
        //handle miss
        if(write_allocate == 1 && write_through == 0) {
          //write back with write allocate
          cycles += cache.handleLoadMiss(memory_address, least_recently_used);
        } else {
          //read from memory
          cycles+= 100;
        }
        load_misses++;

      }

    }

    public static void store(String memory_address) {
      String result = cache.search(memory_address);
      if(write_allocate == 1) {
        //Write through with write allocate
        if(write_through == 1) {
          //handle hit
          if(result.equals("hit")) {
            //writes to cache and to main memory
            cycles++;
            cycles += 100;
            store_hits++;
          } else {//handle miss
            //updates the block in main memory
            //brings block to cache with lru or fifo method
            if(least_recently_used == 1) {
              //use lru method
              Block chosen = cache.leastRecentlyUsed();
              if(chosen.isDirty()) {
                //if the block chosen was dirty
                //data in block was written back to memory
                //add 100 to cycles
                cycles++;
                cycles += 100;
              }
              //read data from memory into cache block
              cache.putDataInCacheBlock(memory_address, chosen.getStartingAddress());
              cycles++;
              cycles+= 100;
            } else {
              //use fifo method
              Block chosen = cache.fifo();
              if(chosen.isDirty()) {
                //if the block chosen was dirty
                //data in block was written back to memory
                //add 100 to cycles
                cycles++;
                cycles += 100;
              }
              //read data from memory into cache block
              cache.putDataInCacheBlock(memory_address, chosen.getStartingAddress());
              cycles++;
              cycles += 100;
            }
            store_misses++;
          }
        } else {//write back with write allocate
          //handle hit
          if(result.equals("hit")) {
            //set block as dirty
            cache.markAsDirty(memory_address);
            cycles++;
            store_hits++;
          } else {//handle miss
            //find cache block to use
            cycles++;
            //brings block to cache with lru or fifo method
            if(least_recently_used == 1) {
              //use lru method
              Block chosen = cache.leastRecentlyUsed();
              if(chosen.isDirty()) {
                //if the block chosen was dirty
                //data in block was written back to memory
                //add 100 to cycles
                cycles++;
                cycles += 100;
              }
              //read data from memory into cache block
              cache.putDataInCacheBlock(memory_address, chosen.getStartingAddress());
              cycles++;
              cycles+= 100;
            } else {
              //use fifo method
              Block chosen = cache.fifo();
              if(chosen.isDirty()) {
                //if the block chosen was dirty
                //data in block was written back to memory
                //add 100 to cycles
                cycles++;
                cycles += 100;
              }
              //read data from memory into cache block
              //read data from memory into cache block
              cache.putDataInCacheBlock(memory_address, chosen.getStartingAddress());
              cycles += 100;
            }
            //mark block as dirty
            cache.markAsDirty(memory_address);
            store_misses++;
          }
        }
      } else {
        //Write through with no write allocate
        if(write_through == 1) {
          //handle hit
          if(result.equals("hit")) {
            //writes to cache and to main memory
            cycles++;
            cycles += 100;
            store_hits++;
          } else {//handle miss
            //updates the block in main memory
            cycles+= 100;
            store_misses++;
          }
        } else {//write back with no write allocate
          //handle hit
          if(result.equals("hit")) {
            //set block as dirty
            cache.markAsDirty(memory_address);
            cycles++;
            store_hits++;
          } else {//handle miss
            //updates block in main memory
            //it is incredibly innefficient but not impossible
            cycles+= 100;
            store_misses++;
          }
        }
      }
    }
}
