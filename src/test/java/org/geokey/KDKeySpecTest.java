package org.geokey;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.*;

/**
 * Created by mriley on 12/17/16.
 */
public class KDKeySpecTest {
	@Test(expected = IllegalArgumentException.class)
	public void testTooManyBits() {
		new KDKeySpec.Builder().addDim(0, 100, 255).setAlphabet(Alphabet.GEO_HASH).build();
	}

	@Test
	public void testSpecSize() {
		KDKeySpec spec = new GeoKey().getSpec();
		Assert.assertThat(spec.toString(), spec.getRange(0, 5*9), is(closeTo(6.06914618556874E-5d, 1e-5)));
		Assert.assertThat(spec.toString(), spec.getRange(1, 5*9), is(closeTo(3.03457309278437E-5, 1e-5)));
	}
}
