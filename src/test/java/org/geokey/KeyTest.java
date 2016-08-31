package org.geokey;

import org.junit.Test;

/**
 * Created by mriley on 8/17/16.
 */
public class KeyTest {

	@Test
	public void testKey() {
		System.err.println(new GeoKey().getSpec());
		System.err.println(new GeoKey().setLatitude(48.669).setLongitude(-4.329).toString());
		System.err.println(new GeoTimeKey().setLatitude(48.669).setLongitude(-4.329).setTime(System.currentTimeMillis() - 1000));
		System.err.println(new GeoTimeKey().setLatitude(48.669).setLongitude(-4.329).setTime(System.currentTimeMillis()));
		System.err.println(new GeoTimeKey().setLatitude(48.669).setLongitude(-4.329).setTime(System.currentTimeMillis() + 1000));

		System.err.println("COMPARE");
		GeoKey geoKey = new GeoKey().setLatitude(48.669).setLongitude(-4.329);
		String s = geoKey.toString();
		GeoKey geoKey1 = new GeoKey(s);
		System.err.println(s + " " + geoKey.getLatitude() + " " + geoKey.getLongitude());
		System.err.println(geoKey1.toString() + " " + geoKey1.getLatitude() + " " + geoKey1.getLongitude());

		System.err.println("COMPARE");
		GeoTimeKey geoTimeKey = new GeoTimeKey()
				.setLatitude(48.669)
				.setLongitude(-4.329)
				.setTime(System.currentTimeMillis());
		s = geoTimeKey.toString();
		GeoTimeKey geoTimeKey1 = new GeoTimeKey(s);
		System.err.println(s + " " + geoTimeKey.getLatitude() + " " + geoTimeKey.getLongitude() + " " + geoTimeKey.getTime());
		System.err.println(geoTimeKey1.toString() + " " + geoTimeKey1.getLatitude() + " " + geoTimeKey1.getLongitude() + " " + geoTimeKey1.getTime());
	}
}
