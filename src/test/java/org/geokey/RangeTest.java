package org.geokey;

import org.junit.Assert;
import org.junit.Test;

import static org.geokey.TestData.*;

/**
 * Created by mriley on 12/17/16.
 */
public class RangeTest {

	@Test
	public void testRange() {
		GeoKey gk1 = new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE);
		GeoKey gk2 = new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE);
		Range<GeoKey> gkrange = new Range<>(gk1, gk2);
		Assert.assertEquals(gk1, gkrange.getMin());
		Assert.assertEquals(gk2, gkrange.getMax());
		Assert.assertEquals(TEST_GEOHASH_STRING, gkrange.toString());

		gk1 = new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE);
		gk2 = new GeoKey().setLatitude(TEST_LATITUDE + 1e-6).setLongitude(TEST_LONGITUDE + 1e-6);
		gkrange = new Range<>(gk1, gk2);
		Assert.assertEquals(TEST_GEOHASH_STRING.substring(0, 8), gkrange.toString());
		Assert.assertTrue(gkrange.contains(gk1));
		Assert.assertTrue(gkrange.contains(gk2));
		Assert.assertFalse(gkrange.contains(new GeoKey().setLatitude(TEST_LATITUDE + 1e-6 + 1e-6)
				.setLongitude(TEST_LONGITUDE + 1e-6 + 1e-6)));
	}
}
