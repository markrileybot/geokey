package org.geokey;

import org.junit.Test;

/**
 * Created by mriley on 8/17/16.
 */
public class KeyTest {

	@Test
	public void testKey() {
		System.err.println(new GeoKey().setLatitude(48.669).setLongitude(-4.329).getString(9));
		System.err.println(new GeoTimeKey().setLatitude(48.669).setLongitude(-4.329).setTime(System.currentTimeMillis() - 1000));
		System.err.println(new GeoTimeKey().setLatitude(48.669).setLongitude(-4.329).setTime(System.currentTimeMillis()));
		System.err.println(new GeoTimeKey().setLatitude(48.669).setLongitude(-4.329).setTime(System.currentTimeMillis() + 1000));
	}
}
