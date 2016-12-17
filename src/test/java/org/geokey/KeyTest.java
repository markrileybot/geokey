package org.geokey;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.*;

/**
 * Created by mriley on 8/17/16.
 */
public class KeyTest {

	private static final double TEST_LATITUDE = 48.669;
	private static final double TEST_LONGITUDE = -4.329;
	private static final String TEST_GEOHASH_STRING = "gbsuv7ztqzpts82uzfwq5e1bp";
	private static final byte[] TEST_GEOHASH_BYTES = new byte[]{122, -79, -83, -97, -7, -73, -21, -100, 32, 90, -5, -71, 98, -76, 42, -86};
	private static final long TEST_TIME = 1472837197691L;
	private static final String TEST_GEOTIME_STRING = "WkmCkyi22inX1nq5MIbt_mmiVNvRNsst";
	private static final byte[] TEST_GEOTIME_BYTES = new byte[] {90, 73, -126, -109, 40, -74, -38, 41, -41, -42, 122, -71, 48, -122, -19, -2, 105, -94, 84, -37, -47, 54, -53, 45};

	@Test
	public void testGeoKeyWrite() {
		Assert.assertEquals(TEST_GEOHASH_STRING, new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).toString());
		Assert.assertEquals(TEST_GEOHASH_STRING.substring(0, 9), new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).toString(9));
		byte[] bytes = new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).toBytes();
		Assert.assertArrayEquals(Arrays.toString(bytes), TEST_GEOHASH_BYTES, bytes);
	}

	@Test
	public void testGeoKeyRead() {
		GeoKey gk = new GeoKey(TEST_GEOHASH_STRING);
		Assert.assertEquals(TEST_LATITUDE, gk.getLatitude(), .001);
		Assert.assertEquals(TEST_LONGITUDE, gk.getLongitude(), .001);
		gk = new GeoKey(TEST_GEOHASH_BYTES);
		Assert.assertEquals(TEST_LATITUDE, gk.getLatitude(), .001);
		Assert.assertEquals(TEST_LONGITUDE, gk.getLongitude(), .001);
	}

	@Test
	public void testGeoTimeKeyWrite() {
		Assert.assertEquals(TEST_GEOTIME_STRING, new GeoTimeKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).setTime(TEST_TIME).toString());
		byte[] bytes = new GeoTimeKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).setTime(TEST_TIME).toBytes();
		Assert.assertArrayEquals(Arrays.toString(bytes), TEST_GEOTIME_BYTES, bytes);
	}

	@Test
	public void testGeoTimesKeyRead() {
		GeoTimeKey gk = new GeoTimeKey(TEST_GEOTIME_STRING);
		Assert.assertEquals(TEST_LATITUDE, gk.getLatitude(), .001);
		Assert.assertEquals(TEST_LONGITUDE, gk.getLongitude(), .001);
		Assert.assertThat((double)gk.getTime(), is(closeTo(TEST_TIME, 1)));
		gk = new GeoTimeKey(TEST_GEOTIME_BYTES);
		Assert.assertEquals(TEST_LATITUDE, gk.getLatitude(), .001);
		Assert.assertEquals(TEST_LONGITUDE, gk.getLongitude(), .001);
		Assert.assertThat((double)gk.getTime(), is(closeTo(TEST_TIME, 1)));
	}

	@Test
	public void testSpecSize() {
		GeoKey gk = new GeoKey();
		Assert.assertThat(gk.getSpec().getRange(0, 5*9), is(closeTo(6.06914618556874E-5d, 1e-5)));
		Assert.assertThat(gk.getSpec().getRange(1, 5*9), is(closeTo(3.03457309278437E-5, 1e-5)));
	}

	@Test
	public void testRange() {
		GeoKey gk1 = new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE);
		GeoKey gk2 = new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE);
		Range<GeoKey> gkrange = new Range<>(gk1, gk2);
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
