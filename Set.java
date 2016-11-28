public class Set {
    int num_blocks;
    int num_addresses;
    Block[] blocks;

    public Set (int num_blocks1, int num_addresses1) {
        num_blocks = num_blocks1;
        num_addresses = num_addresses1;
        blocks = new Block[num_blocks];
    }

    public void init() {
        for (int i = 0; i < num_blocks; i++) {
            blocks[i] = new Block(num_addresses);
            blocks[i].init();
        }

    }

    public boolean read (int x) {
        boolean hit = false;
        for (int i = 0; i < blocks.length; i++) {
            hit = blocks[i].read(x);
            if (hit) {
                return hit;
            }
        }
        return hit;

    }

}
