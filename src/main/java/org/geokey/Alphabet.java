package org.geokey;

import java.util.Arrays;

/**
 * Created by mriley on 8/26/16.
 */
public class Alphabet {

	public static final Alphabet GEO_HASH = new Alphabet(5,
			'0','1','2','3','4','5','6','7','8','9',
			'b','c','d','e','f','g','h','j','k','m',
			'n','p','q','r','s','t','u','v','w','x',
			'y','z');

	public static final Alphabet GEO_TIME_HASH = new Alphabet(6,
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_');


	final char[] chars;
	final int[] indexes;
	final int indexOffset;
	final int maxBitsPerChar;

	public Alphabet(int maxBitsPerChar, char ... chars) {
		this.chars = chars;
		this.maxBitsPerChar = maxBitsPerChar;
		int maxChar = 0, minChar = Integer.MAX_VALUE;
		for(char c : chars) {
			maxChar = Math.max(maxChar, c);
			minChar = Math.min(minChar, c);
		}
		this.indexOffset = minChar;
		this.indexes = new int[maxChar - indexOffset + 1];
		Arrays.fill(indexes, -1);
		for(int i = 0; i < chars.length; i++) indexes[chars[i] - indexOffset] = i;
	}

	@Override
	public String toString() {
		return "Alphabet{" +
				"chars=" + Arrays.toString(chars) +
				", indexes=" + Arrays.toString(indexes) +
				", indexOffset=" + indexOffset +
				", maxBitsPerChar=" + maxBitsPerChar +
				'}';
	}
}
