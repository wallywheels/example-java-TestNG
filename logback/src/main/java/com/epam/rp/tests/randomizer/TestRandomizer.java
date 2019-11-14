package com.epam.rp.tests.randomizer;

import com.epam.rp.tests.MagicRandomizer;
import com.google.common.collect.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Several tests for magic randomizer
 *
 * @author Andrei Varabyeu
 */
public class TestRandomizer {

	private static final int COUNT = 100;

	/*let's say this is error for defined above execution count */
	private static final int PROBABILITY_ERROR = 5;

	private Logger LOGGER = LoggerFactory.getLogger(TestRandomizer.class);

	@Test
	public void testRandomizerGenerator() {
		Assert.assertTrue(Range.closed(0, 10).contains(MagicRandomizer.luckyInt(10)), "Randomizer is lying!");
	}

	@Test
	public void testRandomizerNevenLies() {
		LOGGER.info("Checking that randimizer never lies...");
		for (int i = 0; i < COUNT; i++) {
			Assert.assertTrue(MagicRandomizer.checkYourLucky(100), "Randomizer is lying!");
		}
	}

	@Test
	public void testRandomizerProbability() {
		int yes = 0;

		int probability = 30;

		LOGGER.info("Testing randomizer with {} tryings for {} probability", COUNT, probability);

		for (int i = 0; i < COUNT; i++) {
			if (MagicRandomizer.checkYourLucky(probability)) {
				yes++;
			}
		}

		LOGGER.info("We have {} 'yes' answers from {} asks", yes, COUNT);

		Integer calculatedPercentage = calculatePercentage(COUNT, yes, 1);

		LOGGER.info("Calculated probability is {}", calculatedPercentage);

		Range<Integer> errorRange = Range.closed(probability - PROBABILITY_ERROR, probability + PROBABILITY_ERROR);
		Assert.assertTrue(errorRange.contains(calculatedPercentage),
				String.format("Probability should be in range %s, but actual value is %s", errorRange, calculatedPercentage));

	}

	private Integer calculatePercentage(int count, int value, int scale) {
		return BigDecimal.valueOf(value).divide(BigDecimal.valueOf(count), scale, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100)).intValue();
	}
}
