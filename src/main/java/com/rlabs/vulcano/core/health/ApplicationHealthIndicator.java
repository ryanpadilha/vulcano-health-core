package com.rlabs.vulcano.core.health;

import com.rlabs.vulcano.core.health.Health.Builder;

/**
 * The Application Health Indication.
 *
 * @author Ryan Padilha <ryan.padilha@gmail.com>
 * @since 0.0.1
 *
 */
public class ApplicationHealthIndicator extends AbstractHealthIndicator {

	@Override
	protected void doHealthCheck(Builder builder) {
		builder.up();
	}

}
