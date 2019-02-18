import java.util.BitSet;
//import java.util.*;

public class Cache {

    public int Capacity;      // 2^m bytes
    public int BlockSize;     // B-byte (2^b-byte) blocks
    public int Associativity; // 1 to N

    public int wordoffset;
    public int indexfield;
    public int tagfield;

    public int hit, miss;	// Hit and miss times
    public boolean isHit = false;	// Initially hit is 0.

    public static MainMemory mainMemory;

    private CacheEntry Sets[][];

    BitCalculator bc = new BitCalculator();

    /**
     *
     *  @param capacity         cache capacity in bytes
     *  @param blocksize        size of each block in bytes
     *  @param associativity    the number of blocks in each set
     */
    public Cache(int capacity, int blocksize, int associativity)
    {
        // TODO create the main memory if it doesn't already exist	// CHECK
        this.mainMemory = new MainMemory();
        // TODO create sets of cache entries
        // (you'll need the number of cache entries per set,
        // as well as the lengths of tag, index and word offset fields)
        this.Capacity = capacity;
        this.BlockSize = blocksize;
        this.Associativity = associativity;

        int wordsPerBlock = BlockSize/4;	// Gives you how much word you keep in each data.
        int M = capacity / (blocksize*associativity);	// Index length.

        this.indexfield = bc.bitsToRepr(M);	// How many bits to fit in index field.
        this.wordoffset = bc.bitsToRepr(wordsPerBlock);	// Gives wordoffset bit length
        this.tagfield = 32 - (indexfield + wordoffset);	// Length of tag field bits.

        this.Sets = new CacheEntry[M][associativity];	// Sets initialaized.
        
        // Create sets of all cache entries.
        for (int i = 0; i < M; i++)
            for (int j = 0; j < Associativity; j++)
                Sets[i][j] = new CacheEntry(tagfield, wordsPerBlock);

        System.out.println("Index Size = " + M + "\tIndex Length = " + indexfield + "\tWord Offset = " + wordoffset + "\t\tTag Length = " + tagfield);
    }

    // TODO read the value that resides at the location pointed
    // by address value on Main Memory.
    // if it's not available on cache, load it first, then try reading again
    
    
    public int read (int address)
    {
        // TODO convert the address into binary BitSet
        BitSet bs = BitCalculator.intToBinary(address);	// Convert address to binary

        // TODO extract the tag, index, word offset fields from the address
        int wordoff = Integer.parseInt(BitCalculator.toString(bs.get(0, wordoffset)),2);	// wordoffset field of address as int
        int index = Integer.parseInt(BitCalculator.toString(bs.get(wordoffset, wordoffset+indexfield)), 2);	// get index part from address as int
        int tag = Integer.parseInt(BitCalculator.toString(bs.get(wordoffset+indexfield, 32)), 2);	// get the tag part from address
        
        isHit = false;	// This is needed for 4word data.

        // TODO check each set if the cache entry's valid bit is set
        // and the tag value equates at the index row
        for (int i = 0; i < Associativity; i++)
        	// For every associativity
        {
        	if(Sets[index][i].isValid() && Sets[index][i].compareTag(bs.get(wordoffset+indexfield, bs.size()-1)))	// Check if it is valid and if tags match
            {
                hit++;	// increment hit
                isHit = true;	// we have a hit
                return BitCalculator.toInteger(Sets[index][i].read(wordoff));	// Return the value in the place of cache where we have a hit.
            }
        }
        int tt = 0;	// For reading multiple words
        // TODO record a hit if we can read from cache in first try and return the value
        if(isHit)
        	if(tt < BlockSize/4)
        	{
        		Sets[index][tt].read(wordoffset);
        		tt++;
        		//index--;	// Check this
        	}
        	else
        	{
        		tt = 0;
        		Sets[++index][tt].read(wordoffset);
        		tt++;
        	}

        int [] dataarr = new int[BlockSize / 4];
        // TODO otherwise, record a miss and load the data from mainmemory into the cache
        if(!isHit)	// If not hit
        {
            for (int i = 0; i < dataarr.length; i++) {		// How many words will we read from memory
            	 dataarr[i] = mainMemory.read(address + i);	// Fetch data from mainmemory
			}
            miss++;	// Not hit, increment miss.
            write(dataarr, index, tag);	// Since no hit write data
        }
        
        

        // TODO try reading from cache again but don't score a hit this time
        for (int i = 0; i < Associativity; i++)	{
            if(Sets[index][i].isValid() && Sets[index][i].compareTag(bs.get(wordoffset+indexfield, 32)))	// Check valid bit and tag
            {
            	return BitCalculator.toInteger(Sets[index][i].read(wordoff));	// Corresponding data in cache as integer.
            }
        }
        return 0;
    }

    // TODO write data to the cache entry
    private void write(int[] data, int index, int tag)
    {   Sets[index][(int)(Math.random() * Associativity)].write(data,tag); 	// write to cache in given index to a random associativity(block) tag and data
    }
}