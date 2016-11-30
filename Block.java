public class Block {
    private int num_bytes;
    private int words;
    private boolean dirty = false; //True if dirty, false if not
    private int[] block;
    private long time_stamp;
    private long time_added;

    public Block(int num_bytes) {
        this.num_bytes = num_bytes;
        words = num_bytes/4;
        block = new int[words];
        for (int i = 0; i < block.length; i++) {
            block[i] = 0;
        }
        time_stamp = System.currentTimeMillis();
        time_added = System.currentTimeMillis();
    }

    public String search(int memory_address) {
      for(int i = 0; i < block.length; i++) {
        if(block[i] == memory_address) {
          return "hit";
        }
      }
      return "miss";
    }

    public boolean insertIfEmpty(int memory_address) {
      for(int i = 0; i < block.length; i++) {
        if(block[i] == 0) {
          block[i] = memory_address;
          return true;
        }
      }
      return false;
    }

    public long getTimeStamp() {
      return time_stamp;
    }
    public long getTimeAdded() {
      return time_added;
    }

    public void setTimeAdded(long new_time) {
      time_added = new_time;
    }

    public int getStartingAddress() { return block[0]; }

    public boolean containsAddress(int memory_address) {
      for(int i = 0; i < block.length; i++) {
        if(block[i] == memory_address) {
          return true;
        }
      }
      return false;
    }

    public void touchBlock() {
      time_stamp = System.currentTimeMillis();
    }
    public boolean isDirty() {
      return dirty;
    }

}
