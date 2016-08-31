package org.geokey;

/**
 * Created by mriley on 8/18/16.
 */
public class GeoKey extends KDKey {

	private static final KDKeySpec spec = new KDKeySpec.Builder()
			.addDim(-180, 180, 1)
			.addDim(-90, 90, 1)
			.setAlphabet(Alphabet.GEO_HASH)
			.build();

	public GeoKey(String s) {
		super(spec, s);
	}

	public GeoKey(byte[] s) {
		super(spec, s);
	}

	public GeoKey() {
		super(spec);
	}

	public GeoKey setLatitude(double latitude) {
		set(1, latitude);
		return this;
	}

	public double getLatitude() {
		return super.get(1);
	}

	public GeoKey setLongitude(double longitude) {
		set(0, longitude);
		return this;
	}

	public double getLongitude() {
		return super.get(0);
	}
}
