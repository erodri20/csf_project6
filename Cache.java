public class Cache {
    public int num_sets;
    public int num_blocks;
    public int num_addresses;
    public Set[] sets;
    
    public Cache(int num_sets1, int num_blocks1, int num_addresses1) {
        num_sets = num_sets1;
        num_blocks = num_blocks1;
        num_addresses = num_addresses;
        sets = new Set[num_sets];
    }

    public void init() {
        for (int i = 0; i < num_sets; i++) {
            sets[i] = new Set (num_blocks, num_addresses);
            sets[i].init();
        }

    }    

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

}
