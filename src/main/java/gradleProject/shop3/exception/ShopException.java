package gradleProject.shop3.exception;

import lombok.Getter;

@Getter
public class ShopException extends RuntimeException {
	private final String redirectUrl;

	public ShopException(String message, String redirectUrl) {
		super(message);
		this.redirectUrl = redirectUrl;
	}

	public ShopException(String message, Throwable cause) {
		super(message, cause);
		this.redirectUrl = null;
	}

	public ShopException(String message, String redirectUrl, Throwable cause) {
		super(message, cause);
		this.redirectUrl = redirectUrl;
	}
}