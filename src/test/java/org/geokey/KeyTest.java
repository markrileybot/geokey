package org.geokey;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.geokey.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.*;

/**
 * Created by mriley on 8/17/16.
 */
public class KeyTest {

	@Test
	public void testGeoKeyWrite() {
		Assert.assertEquals(TEST_GEOHASH_STRING, new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).toString());
		Assert.assertEquals(TEST_GEOHASH_STRING.substring(0, 9), new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).toString(9));
		byte[] bytes = new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).toBytes();
		Assert.assertArrayEquals(Arrays.toString(bytes), TEST_GEOHASH_BYTES, bytes);
	}

	@Test(expected = RuntimeException.class)
	public void testGeoKeyWriteError() {
		new GeoKey().setLatitude(TEST_LATITUDE).setLongitude(TEST_LONGITUDE).toString(new Appendable() {
			@Override
			public Appendable append(CharSequence csq) throws IOException {
				throw new IOException();
			}

			@Override
			public Appendable append(CharSequence csq, int start, int end) throws IOException {
				throw new IOException();
			}

			@Override
			public Appendable append(char c) throws IOException {
				throw new IOException();
			}
		});
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
}
