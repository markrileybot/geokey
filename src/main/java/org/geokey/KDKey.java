package org.geokey;

/**
 * N-Dimensional key
 *
 * Created by mriley on 8/18/16.
 */
public class KDKey {

	/**
	 * This array is a lookup table that translates 5-bit positive integer index values into their "Base32 Alphabet"
	 * equivalents as specified in Table 3 of RFC 4648.
	 */
	private static final char[] BASE32_ISH = {
			'0','1','2','3','4','5','6','7','8','9',
			'b','c','d','e','f','g','h','j','k','m',
			'n','p','q','r','s','t','u','v','w','x',
			'y','z'
	};

	private final KDKeySpec spec;
	private double[] d;

	public KDKey(KDKeySpec spec) {
		this.spec = spec;
		this.d = new double[spec.bounds.length];
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
		int ds = d.length;
		double mid = 0;
		double[] highs = new double[ds];
		double[] lows = new double[ds];
		for(int i = 0; i < ds; i++) {
			lows[i] = spec.bounds[i][0];
			highs[i] = spec.bounds[i][1];
		}

		int bits = len * 8;
		for(int i = 0; i < bits; i++) {
			int off = i % ds;
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
		int ds = d.length;
		double mid = 0;
		int tmp = 0, off = 0;
		int bits = resolution == -1 ? ds * 8 * 8 : resolution * 5;

		double[] highs = new double[ds];
		double[] lows = new double[ds];
		for(int i = 0; i < ds; i++) {
			lows[i] = spec.bounds[i][0];
			highs[i] = spec.bounds[i][1];
		}

		for(int i = 0; i < bits; i++) {
			off = i % ds;
			mid = (highs[off] + lows[off]) / 2;
			if(d[off] > mid) {
				tmp = (tmp << 1) | 0x1;
				lows[off] = mid;
			} else {
				tmp = tmp << 1;
				highs[off] = mid;
			}
			if(i % 5 == 4) {
				res.append(BASE32_ISH[tmp]);
				tmp = 0;
			}
		}
	}
}
