import java.util.BitSet;

public class CacheSimulator {

	public static final int ROWS = 100;
	public static final int COLS = 100;

	public static void main(String[] args) {
//    	Cache cache_direct_map = new Cache(1048576, 4, 1);
//    	Cache cache_two_way = new Cache(1048576, 4, 2);
		Cache cache_4_word_block = new Cache(1048576, 16, 1);

		int ArrayBase = Cache.mainMemory.getBaseAddress();

		int coco = 1;

		// Reading row major
		for (int r = 0; r < ROWS; ++r) {
			for (int c = 0; c < COLS; ++c) {
				int arrayIndex = r * ROWS + c;
//                int arrayIndex = ((int)(Math.random() * 10000) + 1);

//                int data = cache_direct_map.read(arrayIndex + ArrayBase);
//                int data = cache_two_way.read(arrayIndex + ArrayBase);
				int data = cache_4_word_block.read(arrayIndex + ArrayBase);
				System.out.println(coco++ + ") " + data + " == " + Cache.mainMemory.mem[r * ROWS + c]);
				assert data == Cache.mainMemory.mem[r * ROWS + c];
//                assert data == Cache.mainMemory.mem[arrayIndex];
			}
		}
//        System.out.println(cache_direct_map.hit);
//        System.out.println(cache_direct_map.miss);
//		System.out.println(cache_two_way.hit);
//		System.out.println(cache_two_way.miss);
		System.out.println(cache_4_word_block.hit);
		System.out.println(cache_4_word_block.miss);

	}
}