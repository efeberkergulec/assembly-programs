import java.util.BitSet;

public class BitCalculator {
    /**
     *  Computes the number of bits required to address a list of values
     *  @param number   the length of list
     */
    public static int bitsToRepr(int number) {
        int bits = 0;

        while (Math.pow(2, bits) < number) {
            ++bits;
        }
        return bits;
    }

    /**
     *  logarithm operation on base 2 that returns an integer
     *  https://stackoverflow.com/a/3305710/
     *  @param number    Operand for the logarithm operation
     */
    public static int log2(int number)
    {
        int log = 0;
        if( ( number & 0xffff0000 ) != 0 ) { number >>>= 16; log = 16; }
        if( number >= 256 ) { number >>>= 8; log += 8; }
        if( number >= 16  ) { number >>>= 4; log += 4; }
        if( number >= 4   ) { number >>>= 2; log += 2; }
        return log + ( number >>> 1 );
    }

    /**
     *  Returns a BitSet binary array object that is the 32 bit representation of the argument
     *
     *  @param number the number to be converted
     */
    public static BitSet intToBinary(int number) {
        BitSet binaryAddress = new BitSet(32);
        binaryAddress.or(fromString(Integer.toBinaryString( number )));
        return binaryAddress;
    }

    public static BitSet fromString(final String s) {
        return BitSet.valueOf(new long[] { Long.parseLong(s, 2) });
    }

    /**
     *  Returns a BitSet object as a String, ignores the leading zero bits.
     *  i.e. will not pad insignificant zeros at the beginning
     *
     *  @param bs    BitSet object to be represented using "0"s and "1"s
     */
    public static String toString(BitSet bs) {
        if (bs.cardinality() == 0) { return "0"; }
        return Long.toString(bs.toLongArray()[0], 2);
    }

    /**
     *  Returns a BitSet object as an integer.
     *
     *  @param bs   BitSet object to be returned as integer in base 10.
     *
     */
    public static int toInteger(BitSet bs) {
        if (bs.cardinality() == 0) { return 0; }
        return Integer.parseInt(BitCalculator.toString(bs), 2);
    }
}
