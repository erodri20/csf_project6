/*

Natasha Bornhorst - nbornho1@jhu.edu
Elise Rodrigues - erodri20@jhu.edu

*/

public class Cache {
    private int num_sets;
    private int num_blocks;
    private int num_bytes;
    private Set[] cache;

    public Cache(int num_sets, int num_blocks, int num_bytes) {
        this.num_sets = num_sets;
        this.num_blocks = num_blocks;
        this.num_bytes = num_bytes;
        cache = new Set[num_sets];
        for (int i = 0; i < num_sets; i++) {
            cache[i] = new Set (num_blocks, num_bytes);
        }
    }

    public String search(String memory_address) {
      for(int i = 0; i < cache.length; i++) {
        if(cache[i].search(memory_address).equals("hit")) {
            return "hit";
        }
      }
      return "miss";
    }

    public int handleLoadMiss(String memory_address, int least_recently_used) {
      //search for empty block
      int cycles = 0;
      for(int i = 0; i < cache.length; i++) {
        if(cache[i].insertIfEmpty(memory_address)) {//returns true if inserted, false otherwise
          cycles += 100;
          cycles++;
          //System.out.println("Memory address for load miss: " + memory_address);
          return cycles;
        }
      }
      //if cache is full, find cache block to use
      cycles++;
      //System.out.println("cache is full");
      if(least_recently_used == 1) {
        Block chosen = leastRecentlyUsed();
        if(chosen.isDirty()) {
          System.out.println("cache block is dirty: " + memory_address);
          //if the block chosen was dirty, add 100 to cycles
          cycles += 100;
          markAsNonDirty(chosen.getStartingAddress());
        }
        //read data from memory into cache block
        putDataInCacheBlock(memory_address, chosen.getStartingAddress());
        cycles += 100;
      } else {
        Block chosen = fifo();
        if(chosen.isDirty()) {
          //if the block chosen was dirty, add 100 to cycles
          cycles += 100;
          markAsNonDirty(chosen.getStartingAddress());
        }
        //read data from memory into cache block
        putDataInCacheBlock(memory_address, chosen.getStartingAddress());
        cycles += 100;
      }
      //mark the cache block as not dirty adds a cycle
      cycles++;
      return cycles;
    }

    public Block fifo() {
      long fifo_time = cache[0].fifo().getTimeAdded();
      Block fifo_block = cache[0].fifo();
      for(int i = 0; i < cache.length; i++) {
        if (cache[i].fifo().getTimeAdded() < fifo_time) {
          fifo_time = cache[i].fifo().getTimeAdded();
          fifo_block = cache[i].fifo();
        }
      }
      resetFifo(fifo_block.getStartingAddress());
      return fifo_block;
    }

    public void resetFifo(String memory_address) {
      for(int i = 0; i < cache.length; i++) {
        if(cache[i].resetFifo(memory_address)) {
          break;
        }
      }
    }

    public Block leastRecentlyUsed() {
      long lru_time = cache[0].leastRecentlyUsed().getTimeStamp();
      Block lru_block = cache[0].leastRecentlyUsed();
      for(int i = 0; i < cache.length; i++) {
        if (cache[i].leastRecentlyUsed().getTimeStamp() < lru_time) {
          lru_time = cache[i].leastRecentlyUsed().getTimeStamp();
          lru_block = cache[i].leastRecentlyUsed();
        }
      }
      return lru_block;
    }

    public void markAsNonDirty(String memory_address) {
      for(int i = 0; i < cache.length; i++) {
        cache[i].markAsNonDirty(memory_address);
      }
    }

    public void markAsDirty(String memory_address) {
      //mark the block as dirty
      for(int i = 0; i < cache.length; i++) {
        if(cache[i].containsBlock(memory_address)) {
          cache[i].markAsDirty(memory_address);
          break;
        }
      }
    }

    public void putDataInCacheBlock(String memory_address, String starting_address) {
      for(int i = 0; i < cache.length; i++) {
        cache[i].putDataInCacheBlock(memory_address, starting_address);
      }
    }

    public void print() {
      for(int i = 0; i < cache.length; i++) {
        cache[i].print();
      }
    }

}
