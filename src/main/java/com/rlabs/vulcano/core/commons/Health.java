package com.rlabs.vulcano.core.commons;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The health class for information about a service.
 *
 * @author Ryan Padilha <ryan.padilha@gmail.com>
 * @since 0.0.1
 *
 */
public class Health {

	private Status status;
	private DependencyType type;
	private final Map<String, Object> details;

	/**
	 * private constructor, instance only by Builder class
	 *
	 * @param builder
	 */
	private Health(Builder builder, DependencyType type) {
		Objects.requireNonNull(builder, "Builder must not be null");

		this.status = builder.status;
		this.type = builder.type;
		this.details = Collections.unmodifiableMap(builder.details);
	}

	public Status getStatus() {
		return status;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public DependencyType getType() {
		return type;
	}

	public void setType(DependencyType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Health other = (Health) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (status != other.status)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Health [status=" + status + ", type=" + type + ", details=" + details + "]";
	}

	public static Builder unknown() {
		return status(Status.UNKNOWN);
	}

	public static Builder up() {
		return status(Status.UP);
	}

	public static Builder down() {
		return status(Status.DOWN);
	}

	public static Builder down(Exception e) {
		return down().withException(e);
	}

	public static Builder outOfService() {
		return status(Status.OUT_OF_SERVICE);
	}

	public static Builder status(Status status) {
		return new Builder(status);
	}

	public static class Builder {

		private Status status;
		private DependencyType type;
		private Map<String, Object> details;

		public Builder() {
			this.status = Status.UNKNOWN;
			this.type = DependencyType.INTERNAL;
			this.details = new LinkedHashMap<>();
		}

		public Builder(Status status) {
			Objects.requireNonNull(status, "Status must not be null.");

			this.status = status;
			this.details = new LinkedHashMap<>();
		}

		public Builder(DependencyType type) {
			Objects.requireNonNull(type, "DependencyType must not be null.");

			this.type = type;
			this.details = new LinkedHashMap<>();
		}

		public Builder(Status status, DependencyType type) {
			Objects.requireNonNull(status, "Status must not be null.");
			Objects.requireNonNull(type, "DependencyType must not be null");

			this.status = status;
			this.type = type;
			this.details = new LinkedHashMap<>();
		}

		public Builder(Status status, Map<String, ?> details) {
			Objects.requireNonNull(status, "Status must not be null.");
			Objects.requireNonNull(details, "Details must not be null.");

			this.status = status;
			this.details = new LinkedHashMap<>(details);
		}

		public Builder(Status status, DependencyType type, Map<String, ?> details) {
			Objects.requireNonNull(status, "Status must not be null.");
			Objects.requireNonNull(type, "DependencyType must not be null");
			Objects.requireNonNull(details, "Details must not be null.");

			this.status = status;
			this.type = type;
			this.details = new LinkedHashMap<>(details);
		}

		public Builder withException(Exception e) {
			Objects.requireNonNull(e, "Exception must not be null.");

			return withDetail("error", e.getClass().getName() + ": " + e.getMessage());
		}

		public Builder withDetail(String key, Object value) {
			this.details.put(key, value);
			return this;
		}

		public Builder unknown() {
			return status(Status.UNKNOWN);
		}

		public Builder up() {
			return status(Status.UP);
		}

		public Builder down() {
			return status(Status.DOWN);
		}

		public Builder down(Exception e) {
			return down().withException(e);
		}

		public Builder outOfService() {
			return status(Status.OUT_OF_SERVICE);
		}

		public Builder status(Status status) {
			this.status = status;
			return this;
		}

		public Health build() {
			return new Health(this, this.type);
		}

	}
}
