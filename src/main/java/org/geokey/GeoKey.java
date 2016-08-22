package org.geokey;

/**
 * Created by mriley on 8/18/16.
 */
public class GeoKey extends KDKey {

	private static final KDKeySpec spec = new KDKeySpec(
			new double[][] {{-180,180},{-90,90}}
	);

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
