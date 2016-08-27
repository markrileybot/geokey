package org.geokey;

/**
 * Created by mriley on 8/26/16.
 */
public class Alphabet {

	public static final Alphabet GEO_HASH = new Alphabet(5,
			'0','1','2','3','4','5','6','7','8','9',
			'b','c','d','e','f','g','h','j','k','m',
			'n','p','q','r','s','t','u','v','w','x',
			'y','z');

	public static final Alphabet GEO_TIME_HASH = new Alphabet(4,
			'0','1','2','3','4','5','6','7','8','9',
			'b','c','d','e','f','g','h','j','k','m',
			'n','p','q','r','s','t','u','v','w','x',
			'y','z');


	final char[] chars;
	final int maxBitsPerChar;

	public Alphabet(int maxBitsPerChar, char ... chars) {
		this.chars = chars;
		this.maxBitsPerChar = maxBitsPerChar;
	}
}
