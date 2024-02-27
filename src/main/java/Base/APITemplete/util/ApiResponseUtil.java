package Base.APITemplete.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {

    // T is a type parameter for the ApiResponse class
    public static <T> ResponseEntity<?> buildApiResponse(String status, int code, String message, T data) {
        // Inside this method, T represents a type that will be determined at runtime
        // Create an instance of ApiResponse with the specified type T
        return new ResponseEntity<>(new ApiResponse<>(status, code, message, data), HttpStatus.valueOf(code));
    }

    // Generic response structure for an item
    static class ApiResponse<T> {
        private String status;
        private int code;
        private String message;
        private T data;

        public ApiResponse(String status, int code, String message, T data) {
            this.status = status;
            this.code = code;
            this.message = message;
            this.data = data;
        }

        //Getters
        public String getStatus() {
            return status;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }

    }
}
