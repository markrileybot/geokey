package org.geokey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mriley on 8/18/16.
 */
public class KDKeySpec {

	public static final class Builder {
		private final List<Double> mins = new ArrayList<>();
		private final List<Double> maxs = new ArrayList<>();
		private final List<Integer> bitss = new ArrayList<>();
		private Alphabet alphabet;

		public Builder addDim(double min, double max, int bits) {
			mins.add(min);
			maxs.add(max);
			bitss.add(bits);
			return this;
		}

		public Builder setAlphabet(Alphabet alphabet) {
			this.alphabet = alphabet;
			return this;
		}

		public KDKeySpec build() {
			double[] l = new double[mins.size()];
			double[] h = new double[maxs.size()];

			int i = 0;
			for(Double d : mins) l[i++] = d;
			i = 0;
			for(Double d : maxs) h[i++] = d;

			int s = 0;
			i = 0;
			List<Integer> bitOffs = new ArrayList<>();
			for(Integer v : bitss) {
				s += v;
				for(int ii = 0; ii < v; ii++) {
					bitOffs.add(i);
				}
				i++;
			}
			if(s > alphabet.maxBitsPerChar) {
				throw new IllegalArgumentException("Total bits must be <= alphabet bits");
			}

			i = 0;
			int[] bo = new int[bitOffs.size()];
			for(Integer v : bitOffs) bo[i++] = v;
			return new KDKeySpec(alphabet, l, h, bo, s);
		}
	}

	final Alphabet alphabet;
	final double[] mins;
	final double[] maxs;
	final int[] bitOffs;
	final int bits;

	public KDKeySpec(Alphabet alphabet, double[] mins, double[] maxs, int[] bitOffs, int bits) {
		this.alphabet = alphabet;
		this.mins = mins;
		this.maxs = maxs;
		this.bitOffs = bitOffs;
		this.bits = bits;
	}

	public double getRange(int dimension, int bits) {
		double c = 0;
		double segs = bits / (double)this.bits;
		for(int b : bitOffs) {
			if(b == dimension) c+=1;
		}
		return (maxs[dimension] - mins[dimension]) / Math.pow(2, segs * c);
	}

	@Override
	public String toString() {
		return "KDKeySpec{" +
				"alphabet=" + alphabet +
				", mins=" + Arrays.toString(mins) +
				", maxs=" + Arrays.toString(maxs) +
				", bitOffs=" + Arrays.toString(bitOffs) +
				", bits=" + bits +
				'}';
	}
}
