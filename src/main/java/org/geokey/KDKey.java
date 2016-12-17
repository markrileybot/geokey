package org.geokey;

import java.io.IOException;

/**
 * N-Dimensional key
 *
 * Created by mriley on 8/18/16.
 */
public class KDKey {

	private final KDKeySpec spec;
	private double[] d;

	public KDKey(KDKeySpec spec) {
		this.spec = spec;
		this.d = new double[spec.maxs.length];
	}

	public KDKey(KDKeySpec spec, String s) {
		this.spec = spec;
		this.d = new double[spec.maxs.length];
		fromString(s);
	}

	public KDKey(KDKeySpec spec, byte[] s) {
		this.spec = spec;
		this.d = new double[spec.maxs.length];
		fromBytes(s);
	}

	public KDKeySpec getSpec() {
		return spec;
	}

	public KDKey set(int i, double d) {
		this.d[i] = d;
		return this;
	}

	public double get(int i) {
		return this.d[i];
	}

	public KDKey fromBytes(byte[] s) {
		int bpc = spec.bits;
		int[] bitOffs = spec.bitOffs;
		int ds = d.length;
		double mid = 0;
		int off = 0, v = 0, b = 0;

		double[] highs = new double[ds];
		double[] lows = new double[ds];
		System.arraycopy(spec.maxs, 0, highs, 0, ds);
		System.arraycopy(spec.mins, 0, lows, 0, ds);

		int bits = s.length * 8;
		for (int i = 0; i < bits; i++) {
			b = s[i/8];
			off = bitOffs[i % bpc];
			v = (b >> (7 - (i % 8))) & 0x1;
			mid = (highs[off] + lows[off]) / 2;
			if(v == 1) {
				d[off] = mid;
				lows[off] = mid;
			} else {
				d[off] = lows[off];
				highs[off] = mid;
			}
		}
		return this;
	}

	public KDKey fromString(String s) {
		char[] chars = s.toCharArray();
		int alphaOff = spec.alphabet.indexOffset;
		int[] alphaIdx = spec.alphabet.indexes;
		int mbpc = spec.alphabet.maxBitsPerChar;
		int bpc = spec.bits;
		int[] bitOffs = spec.bitOffs;
		int ds = d.length;
		double mid = 0;
		int off = 0, v = 0, ch = 0;

		double[] highs = new double[ds];
		double[] lows = new double[ds];
		System.arraycopy(spec.maxs, 0, highs, 0, ds);
		System.arraycopy(spec.mins, 0, lows, 0, ds);

		int bits = chars.length * mbpc;
		for (int i = 0; i < bits; i++) {
			ch = alphaIdx[chars[i / mbpc] - alphaOff];
			off = bitOffs[i % bpc];
			v = (ch >> (mbpc - (i % mbpc) - 1)) & 0x1;
			mid = (highs[off] + lows[off]) / 2;
			if(v == 1) {
				d[off] = mid;
				lows[off] = mid;
			} else {
				d[off] = lows[off];
				highs[off] = mid;
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return toString(-1);
	}

	public byte[] toBytes() {
		byte[] r = new byte[d.length * 8];
		toBytes(r, 0, r.length);
		return r;
	}

	public void toBytes(byte[] res, int offset, int len) {
		int bpc = spec.bits;
		int[] bitOffs = spec.bitOffs;
		int ds = d.length;
		double mid = 0;
		double[] highs = new double[ds];
		double[] lows = new double[ds];
		System.arraycopy(spec.maxs, 0, highs, 0, ds);
		System.arraycopy(spec.mins, 0, lows, 0, ds);

		int bits = len * 8;
		for(int i = 0; i < bits; i++) {
			int off = bitOffs[i % bpc];
			int byteIdx = i / 8 + offset;
			mid = (highs[off] + lows[off]) / 2;
			res[byteIdx] <<= 1;
			if(d[off] > mid) {
				res[byteIdx] += 1;
				lows[off] = mid;
			} else {
				highs[off] = mid;
			}
		}
	}

	public String toString(int resolution) {
		StringBuilder res = new StringBuilder();
		toString(res, resolution);
		return res.toString();
	}

	public void toString(Appendable res) {
		toString(res, -1);
	}

	public void toString(Appendable res, int resolution) {
		int mbpc = spec.alphabet.maxBitsPerChar;
		char[] chars = spec.alphabet.chars;
		int bpc = spec.bits;
		int[] bitOffs = spec.bitOffs;
		int ds = d.length;
		double mid = 0;
		int tmp = 0, off = 0;
		int bits = resolution <= -1 ? ds * 8 * 8 : resolution * mbpc;

		double[] highs = new double[ds];
		double[] lows = new double[ds];
		System.arraycopy(spec.maxs, 0, highs, 0, ds);
		System.arraycopy(spec.mins, 0, lows, 0, ds);

		for(int i = 0; i < bits; i++) {
			off = bitOffs[i % bpc];
			mid = (highs[off] + lows[off]) / 2;
			tmp <<= 1;
			if(d[off] > mid) {
				tmp += 1;
				lows[off] = mid;
			} else {
				highs[off] = mid;
			}
			if((i % mbpc) == (mbpc - 1)) {
				try {
					res.append(chars[tmp]);
				} catch (IOException ignored) {
					throw new RuntimeException(ignored);
				}
				tmp = 0;
			}
		}
	}
}
