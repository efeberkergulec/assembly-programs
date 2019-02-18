import java.util.logging.*;

public class MainMemory {

    private int matrix_size = 128 * 128; // In words because integer
    private int memBase = 268501024;

    public int mem[];

    public MainMemory() {

        mem = new int[this.matrix_size];

        for (int i = 0; i < this.matrix_size; i++) {
            mem[i] = (int) Math.floor(Math.random() * 10000) + 1;
        }
    }

    public int getBaseAddress() {
        return this.memBase;
    }

    public int read (int address) {
        int index = address - memBase;

        return mem[index];
    }
}