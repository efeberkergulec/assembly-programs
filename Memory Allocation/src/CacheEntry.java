import java.util.BitSet;
import java.util.logging.Level;

public class CacheEntry {

	/*
	 * Valid Field Tag Field Data Field
	 */

	BitSet bits;

	private int tagBitsLength;
	private int wordsPerBlock;
	private int validBitIndex;

	public CacheEntry(int tagBits, int wordsPerBlock) {
		// TODO initialize BitSet bits to the correct length // CHECK
		// TODO calculate the bit indices

		this.wordsPerBlock = wordsPerBlock; // Assign wordsperblock
		this.validBitIndex = (wordsPerBlock * 32) + tagBits; // Assign valid index to .
		this.tagBitsLength = tagBits;	// length is directly tagBitsLength parameter comes as like 14.

		bits = new BitSet((wordsPerBlock * 32) + tagBits + 1); // 1 word = 4 byte 1 byte = 8 bit.
	}

	public Boolean isValid() {
			return bits.get(validBitIndex);	// If value in validBitIndex is 1, Return true.
	}

	//	TODO FIX THIS
	public Boolean compareTag(BitSet cacheTagField) {
		// TODO are the bits on the tag field of our cache entry overlap with the
		// argument bits?
		BitSet anlam = bits.get(wordsPerBlock * 32, wordsPerBlock*32+tagBitsLength);	// tag in our bits
		if (anlam.equals(cacheTagField)) {	// If tag is equal with what we get from address space
			return true; 	// true
		}
		return false;	// Else return false
	}

	public BitSet read(int wordOffset) {

		return bits.get(wordOffset * 32, wordOffset * 32 + 32); // Get the right word

		// TODO return the bits from our data portion that is offset to the correct word
	}

	public void write(int[] data, int tag) {
		// TODO load a new block to the cache, populate the BitSet object
		int dataoffset = 0; // Initially 0, used for keeping in which index we are in bits.
		validBitIndex = (wordsPerBlock*32) + tagBitsLength; // Set the valid bit index.
       
		for (int i = 0; i < data.length; i++) {
			// For every word
			BitSet binarydata = BitCalculator.intToBinary(data[i]);	// Convert it into binary
			String ss = BitCalculator.toString(binarydata);	// Convert it into string
			
			
			int datalen = 32 - ss.length();	// Amount of 0's to be put.
			for (int j = 0; j < datalen; j++) {
				ss= "0"+ss;	// to left of word to make it 32 bit
				
			}

			int temp = 0;	// Used for each word
			for (int j = ss.length() -1; j >= 0; j--) {	// Iterate over our word from right to left.
				if (ss.charAt(j) == '1')	// If it is 1
					bits.set(temp + dataoffset);	// Set the temp + dataoffset index to 1 in bits.
				temp++ ;	// Inc temp
				
			}
			dataoffset += 32;	// Update where to write
		}
		
		
		
		BitSet tagbin = BitCalculator.intToBinary(tag); // Convert tag into binary
		String tagstr = BitCalculator.toString(tagbin); // Convert tagbin to str.
		int len = tagstr.length();	// Length of string version
		
		for (int i = 0; i < tagBitsLength - len; i++) {	// Fill the left side of tagstr
			tagstr = '0' + tagstr;	// With 0's.
		}
		
		for (int i = tagstr.length() - 1; i >= 0; i--) {
			if (tagstr.charAt(i) == '1')	// Iterate over tagstr from right to left check if it is 1
				bits.set(dataoffset); // Set the current bit.
			dataoffset++;	// Updated bits index
		}

		validBitIndex = dataoffset;	// After tag index updated we are in bitindex for bits.
		bits.set(validBitIndex);	// Set valid bit index.
		
		
		
	
	}
}