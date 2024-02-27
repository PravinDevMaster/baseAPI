package Base.APITemplete.controller;

import Base.APITemplete.model.AuthRequest;
import Base.APITemplete.model.User;
import Base.APITemplete.service.UserService;
import Base.APITemplete.util.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Endpoints to managing users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<User> userList= userService.getAll();
        if (!userList.isEmpty()) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User retrieved successfully", userList);
        } else {
            return ApiResponseUtil.buildApiResponse("error", HttpStatus.NOT_FOUND.value(), "No user found", null);
        }

    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User retrieved successfully", userService.getById(id));
    }

    @Operation(summary = "Create a new user", description = "Creates a new user. The user login ID must be unique.")
    @PostMapping("/add")
    public ResponseEntity<?> postUser(@RequestBody User user) {
        User createdUser = userService.postUser(user);
        if(createdUser != null) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.CREATED.value(), "User created successfully", createdUser);
        } else {
            return ApiResponseUtil.buildApiResponse("error", HttpStatus.BAD_REQUEST.value(), "Login ID already exists", null);
        }


    }

    @Operation(summary = "Update an existing user", description = "Updates an existing user.")
    @PutMapping("/{id}")
    public ResponseEntity<?> putUser(@RequestBody User user, @PathVariable Long id) {
        return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User update successfully", userService.putUser(user, id));
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User delete successfully", null);
    }

    @Operation(
            summary = "User Login",
            description = "Authenticates a user. If successful, a token will be generated for subsequent requests. Note: Token generation is handled separately."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest user) {
        String loginId = user.getLoginId();
        String password = user.getPassword();

        User user1 = userService.validateUser(loginId, password);
        if (user1 != null) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "Login successful!", user1);
        } else {
            return  ApiResponseUtil.buildApiResponse("error", HttpStatus.UNAUTHORIZED.value(), "Invalid username or password", null);
        }

    }

    @Operation(
            summary = "Change User Password",
            description = "Changes the password for the authenticated user."
    )
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody AuthRequest user) {
        User user1 = userService.changePassword(user.getLoginId(), user.getPassword());
        if (user1 != null) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "Password change successfully", user1);
        } else {
            return ApiResponseUtil.buildApiResponse("error", HttpStatus.NOT_FOUND.value(), "User not found", null);
        }
    }

}
