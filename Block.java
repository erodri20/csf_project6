public class Block {
    public int full_size;
    public int size;
    public int[] addresses;

    public Block(int full_size1) {
        full_size = full_size1;
        size = full_size/4;
        addresses = new int[size];
    }

    public void init() {
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = 0;
        }
    }


    public boolean read(int x) {
        boolean hit = false;

        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == x) {
            hit = true;
            return hit;
            }
        }
        return hit;
    }
}
