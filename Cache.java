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

    public String search(int memory_address) {
      for(int i = 0; i < cache.length; i++) {
        if(cache[i].search(memory_address).equals("hit")) {
            return "hit";
        }
      }
      return "miss";
    }

    public int handleLoadMiss(int memory_address, int least_recently_used) {
      //search for empty block
      int cycles;
      for(int i = 0; i < cache.length; i++) {
        if(cache[i].insertIfEmpty(memory_address)) {//returns true if inserted, false otherwise
          //cycles = cycles + 100 + 1;
          return cycles;
        }
      }

      //if miss (cache is full)
      //if LRU, call LRU to find block to use
      if(least_recently_used == 1) {
        leastRecentlyUsed(memory_address);
      } else {
        //fifo
        fifo(memory_address);
      }
        //if FIFO, call FIFO to find block to use
        //block is result of lru or fifo
        //if block is dirty
          // add 100 to cycles to write back to memory
        // add 1 to cycles to read into cache
        //mark as not dirty
    }

    public boolean fifo(int memory_address) {
      //update the fifo
      int fifo_time = cache[0].fifo().getTimeAdded();
      Block fifo_block = cache[0].fifo();
      for(int i = 0; i < cache.length; i++) {
        if (cache[i].fifo().getTimeAdded() < fifo_time) {
          fifo_time = cache[i].fifo().getTimeAdded();
          fifo_block = cache[i].fifo();
        }
      }
      return fifo_block.isDirty();
    }

    public boolean leastRecentlyUsed(int memory_address) {
      int lru_time = cache[0].leastRecentlyUsed().getTimeStamp();
      Block lru_block = cache[0].leastRecentlyUsed();
      for(int i = 0; i < cache.length; i++) {
        if (cache[i].leastRecentlyUsed().getTimeStamp() < lru_time) {
          lru_time = cache[i].leastRecentlyUsed().getTimeStamp();
          lru_block = cache[i].leastRecentlyUsed();
        }
      }
      return lru_block.isDirty();
}
/*

    public boolean read(int x) {
        boolean hit = false;
        for (int i = 0; i < sets.length; i++) {
            hit = sets[i].read(x);
            if (hit) {
                return hit;
            }
        }
        return hit;
    }
    */

}
