package org.renjin.stats.internals.distributions;

import beast.util.Randomizer;

public class context {
	public static double rng_unif_rand() {
		return Randomizer.nextDouble();
	}
}
