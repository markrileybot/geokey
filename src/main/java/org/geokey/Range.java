package org.geokey;

import java.io.IOException;

/**
 * Created by mriley on 9/4/16.
 */
public class Range<T extends KDKey> {

	private final T min;
	private final T max;

	public Range(T min, T max) {
		this.min = min;
		this.max = max;

		double tmp1 = 0;
		double tmp2 = 0;
		for(int i = 0; i < min.getSpec().maxs.length; i++) {
			if((tmp1 = min.get(i)) > (tmp2 = max.get(i))) {
				max.set(i, tmp1);
				min.set(i, tmp2);
			}
		}
	}

	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}

	public boolean contains(T key) {
		for(int i = 0; i < min.getSpec().maxs.length; i++) {
			double v = key.get(i);
			if(v < min.get(i) || v > max.get(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		min.toString(res);
		max.toString(new Appendable() {
			private boolean done;
			private int idx;
			@Override
			public Appendable append(CharSequence csq) throws IOException {
				return this;
			}
			@Override
			public Appendable append(CharSequence csq, int start, int end) throws IOException {
				return this;
			}
			@Override
			public Appendable append(char c) throws IOException {
				if(!done && c != res.charAt(idx++)) {
					done = true;
					res.delete(idx-1, res.length());
				}
				return this;
			}
		});
		return res.toString();
	}
}
