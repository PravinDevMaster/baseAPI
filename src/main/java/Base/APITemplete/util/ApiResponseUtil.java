package Base.APITemplete.util;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {

    // T is a type parameter for the ApiResponse class
    public static <T> ResponseEntity<?> buildApiResponse(String status, int code, String message, T data) {
        // Inside this method, T represents a type that will be determined at runtime
        // Create an instance of ApiResponse with the specified type T
        return new ResponseEntity<>(new ApiResponse<>(status, code, message, data), HttpStatusCode.valueOf(code));
    }

    // Generic response structure for an item
    @Getter
    static class ApiResponse<T> {
        //Getters
        private final String status;
        private final int code;
        private final String message;
        private final T data;

        public ApiResponse(String status, int code, String message, T data) {
            this.status = status;
            this.code = code;
            this.message = message;
            this.data = data;
        }

    }
}
