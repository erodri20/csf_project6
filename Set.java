public class Set {
    private int num_blocks;
    private int num_addresses;
    private Block[] set;

    public Set(int num_blocks, int num_bytes) {
        this.num_blocks = num_blocks;
        this.num_addresses = num_bytes;
        set = new Block[num_blocks];
        for (int i = 0; i < num_blocks; i++) {
            set[i] = new Block(num_bytes);
        }
    }

    public String search(int memory_address) {
      for(int i = 0; i < set.length; i++) {
        if(set[i].search(memory_address).equals("hit")) {
            set[i].touchBlock();
            return "hit";
        }
      }
      return "miss";
    }

    public boolean insertIfEmpty(int memory_address) {
      for(int i = 0; i < set.length; i++) {
        if(set[i].insertIfEmpty(memory_address)) {
          set[i].touchBlock();
          return true;
        }
      }
      return false;
    }

    public Block fifo() {
      long fifo_time = set[0].getTimeAdded();
      Block fifo_block = set[0];
      for(int i = 0; i < set.length; i++) {
        if (set[i].getTimeAdded() < fifo_time) {
          fifo_time = set[i].getTimeAdded();
          fifo_block = set[i];
        }
      }
      return fifo_block;
    }

    public boolean resetFifo(int memory_address) {
      for(int i = 0; i < set.length; i++) {
        if(set[i].containsAddress(memory_address)) {
          set[i].setTimeAdded(System.currentTimeMillis());
          return true;
        }
      }
      return false;
    }

    public Block leastRecentlyUsed() {
      long lru_time = set[0].getTimeStamp();
      Block lru_block = set[0];
      for(int i = 0; i < set.length; i++) {
        if (set[i].getTimeStamp() < lru_time) {
          lru_time = set[i].getTimeStamp();
          lru_block = set[i];
        }
        //if this set contains the lru block
          //call least recently used method on that block
          //return whether or not it was dirty
      }

      return lru_block;
    }

}
