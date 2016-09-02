package org.geokey;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.number.IsCloseTo.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by mriley on 8/17/16.
 */
public class KeyTest {

	@Test
	public void testGeoKeyWrite() {
		Assert.assertEquals("gbsuv7ztqzpts82uzfwq5e1bp", new GeoKey().setLatitude(48.669).setLongitude(-4.329).toString());
		Assert.assertEquals("gbsuv7ztq", new GeoKey().setLatitude(48.669).setLongitude(-4.329).toString(9));
	}

	@Test
	public void testGeoKeyRead() {
		GeoKey gk = new GeoKey("gbsuv7ztqzpts82uzfwq5e1bp");
		Assert.assertEquals(48.669, gk.getLatitude(), .001);
		Assert.assertEquals(-4.329, gk.getLongitude(), .001);
	}

	@Test
	public void testGeoTimeKeyWrite() {
		Assert.assertEquals("WkmCkyi22inX1nq5MIbt_mmiVNvRNsst", new GeoTimeKey().setLatitude(48.669).setLongitude(-4.329).setTime(1472837197691L).toString());
	}

	@Test
	public void testGeoTimesKeyRead() {
		GeoTimeKey gk = new GeoTimeKey("WkmCkyi22inX1nq5MIbt_mmiVNvRNsst");
		Assert.assertEquals(48.669, gk.getLatitude(), .001);
		Assert.assertEquals(-4.329, gk.getLongitude(), .001);
		Assert.assertThat((double)gk.getTime(), is(closeTo(1472837197691L, 1)));
	}
}
