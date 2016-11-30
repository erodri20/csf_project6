public class Block {
    private int num_bytes;
    private int words;
    private boolean dirty = false; //True if dirty, false if not
    private String[] block;
    private long time_stamp;
    private long time_added;

    public Block(int num_bytes) {
        this.num_bytes = num_bytes;
        words = num_bytes/4;
        block = new String[words];
        for (int i = 0; i < block.length; i++) {
            block[i] = "";
        }
        time_stamp = System.currentTimeMillis();
        time_added = System.currentTimeMillis();
    }

    public String search(String memory_address) {
      for(int i = 0; i < block.length; i++) {
        if(block[i].equals(memory_address)) {
          return "hit";
        }
      }
      return "miss";
    }

    public boolean containsAddress(String memory_address) {
      for(int i = 0; i < block.length; i++) {
        if(block[i].equals(memory_address)) {
          return true;
        }
      }
      return false;
    }

    public boolean insertIfEmpty(String memory_address) {
      for(int i = 0; i < block.length; i++) {
        if(block[i].equals("")) {
          block[i] = memory_address;
          return true;
        }
      }
      return false;
    }

    public void insert(String memory_address) {
      block[0] = memory_address;
      /*
      for(int i = 1; i < block.length; i++) {
        block[i] = "";
      }
      */
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

    public String getStartingAddress() { return block[0]; }

    public void touchBlock() {
      time_stamp = System.currentTimeMillis();
    }
    public boolean isDirty() {
      return dirty;
    }

    public void markAsNonDirty() {
      dirty = false;
    }

    public void markAsDirty() {
      dirty = true;
    }

    public void print() {
      for(int i = 0; i < block.length; i++) {
        System.out.print(block[i] + " ");
      }
      System.out.println(dirty);
    }

}
