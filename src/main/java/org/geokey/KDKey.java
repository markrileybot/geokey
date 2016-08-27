package org.geokey;

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

	@Override
	public String toString() {
		return getString();
	}


	public byte[] getBytes() {
		byte[] r = new byte[d.length * 8];
		getBytes(r, 0, r.length);
		return r;
	}

	public void getBytes(byte[] res, int offset, int len) {
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
			if(d[off] > mid) {
				res[byteIdx] = (byte) ((res[byteIdx] << 1) | 0x1);
				lows[off] = mid;
			} else {
				highs[off] = mid;
			}
		}
	}

	public String getString() {
		StringBuilder res = new StringBuilder();
		getString(res, -1);
		return res.toString();
	}

	public String getString(int resolution) {
		StringBuilder res = new StringBuilder();
		getString(res, resolution);
		return res.toString();
	}

	public void getString(StringBuilder res) {
		getString(res, -1);
	}

	public void getString(StringBuilder res, int resolution) {
		int mbpc = spec.alphabet.maxBitsPerChar;
		char[] chars = spec.alphabet.chars;
		int bpc = spec.bits;
		int[] bitOffs = spec.bitOffs;
		int ds = d.length;
		double mid = 0;
		int tmp = 0, off = 0;
		int bits = resolution == -1 ? ds * 8 * 8 : resolution * 5;

		double[] highs = new double[ds];
		double[] lows = new double[ds];
		System.arraycopy(spec.maxs, 0, highs, 0, ds);
		System.arraycopy(spec.mins, 0, lows, 0, ds);

		for(int i = 0; i < bits; i++) {
			off = bitOffs[i % bpc];
			mid = (highs[off] + lows[off]) / 2;
			if(d[off] > mid) {
				tmp = (tmp << 1) | 0x1;
				lows[off] = mid;
			} else {
				tmp = tmp << 1;
				highs[off] = mid;
			}
			if((i % mbpc) == (mbpc - 1)) {
				res.append(chars[tmp]);
				tmp = 0;
			}
		}
	}
}
