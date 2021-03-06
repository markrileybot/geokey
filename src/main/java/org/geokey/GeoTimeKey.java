package org.geokey;

/**
 * Created by mriley on 8/18/16.
 */
public class GeoTimeKey extends KDKey {

	private static final KDKeySpec spec = new KDKeySpec.Builder()
			.addDim(-180, 180, 1)
			.addDim(-90, 90, 1)
			.addDim(0, 1L << 62, 1)
			.setAlphabet(Alphabet.GEO_TIME_HASH)
			.build();

	public GeoTimeKey() {
		super(spec);
	}

	public GeoTimeKey(String s) {
		super(spec, s);
	}

	public GeoTimeKey(byte[] s) {
		super(spec, s);
	}

	public GeoTimeKey setLatitude(double latitude) {
		set(1, latitude);
		return this;
	}

	public double getLatitude() {
		return super.get(1);
	}

	public GeoTimeKey setLongitude(double longitude) {
		set(0, longitude);
		return this;
	}

	public double getLongitude() {
		return super.get(0);
	}

	public GeoTimeKey setTime(long time) {
		set(2, time);
		return this;
	}

	public long getTime() {
		return (long) get(2);
	}
}
