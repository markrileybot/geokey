# geokey

K Dimensional Z-Order curve utils.

[![Build Status](https://travis-ci.org/markrileybot/geokey.svg?branch=master)](https://travis-ci.org/markrileybot/geokey) 
[![Coverage Status](https://coveralls.io/repos/github/markrileybot/geokey/badge.svg?branch=master)](https://coveralls.io/github/markrileybot/geokey?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.markrileybot/geokey/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.markrileybot/geokey)

## Building

./gradlew build

## Gradle dependency

See https://search.maven.org/artifact/io.github.markrileybot/geokey/

## Using

### Use built in keys to make geohashes

```java
import org.geokey.GeoKey;

// Make a geo hash key
String key = new GeoKey().setLatitude(48.669).setLongitude(-4.329).toString(); // "gbsuv7ztqzpts82uzfwq5e1bp"

// parse a geo hash key
GeoKey gk = new GeoKey("gbsuv7ztqzpts82uzfwq5e1bp");
```

### Make a special purpose K-Dimensional key

```java
public class GeoTimeKey extends KDKey {

	private static final KDKeySpec spec = new KDKeySpec.Builder()
			.addDim(-180, 180, 1)
			.addDim(-90, 90, 1)
			.addDim(0, 1L << 62, 1)
			.setAlphabet(Alphabet.GEO_TIME_HASH)
			.build();

	public GeoTimeKey() {
		super(spec);
	}

	public GeoTimeKey(String s) {
		super(spec, s);
	}

	public GeoTimeKey(byte[] s) {
		super(spec, s);
	}

	public GeoTimeKey setLatitude(double latitude) {
		set(1, latitude);
		return this;
	}

	public double getLatitude() {
		return super.get(1);
	}

	public GeoTimeKey setLongitude(double longitude) {
		set(0, longitude);
		return this;
	}

	public double getLongitude() {
		return super.get(0);
	}

	public GeoTimeKey setTime(long time) {
		set(2, time);
		return this;
	}

	public long getTime() {
		return (long) get(2);
	}
}
```
