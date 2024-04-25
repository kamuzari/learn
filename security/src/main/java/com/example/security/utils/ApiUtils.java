package com.example.security.utils;

public class ApiUtils {
	public static <T> ApiResult<T> success(T response) {
		return new ApiResult<>(true, response);
	}

	public static ApiResult<?> error(Throwable throwable) {
		return new ApiResult<>(false, new ApiError(throwable));
	}

	public static ApiResult<?> error(String message) {
		return new ApiResult<>(false, new ApiError(message));
	}

	public static class ApiError {
		private final String message;

		ApiError(Throwable throwable) {
			this(throwable.getMessage());
		}

		ApiError(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	public static class ApiResult<T> {
		private final boolean success;
		private final T response;

		private ApiResult(boolean success, T response) {
			this.success = success;
			this.response = response;
		}

		public boolean isSuccess() {
			return success;
		}

		public T getResponse() {
			return response;
		}

	}
}
