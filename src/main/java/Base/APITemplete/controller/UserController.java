package Base.APITemplete.controller;

import Base.APITemplete.model.User;
import Base.APITemplete.service.UserService;
import Base.APITemplete.util.ApiResponseUtil;
import Base.APITemplete.util.EncryptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<User> userList= userService.getAll();
        if (!userList.isEmpty()) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User retrieved successfully", userList);
        } else {
            return ApiResponseUtil.buildApiResponse("error", HttpStatus.NOT_FOUND.value(), "No user found", null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User retrieved successfully", userService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> postUser(@RequestBody User user) {
        User createdUser = userService.postUser(user);
        if(createdUser != null) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.CREATED.value(), "User created successfully", createdUser);
        } else {
            return ApiResponseUtil.buildApiResponse("error", HttpStatus.BAD_REQUEST.value(), "Login ID already exists", null);
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putUser(@RequestBody User user, @PathVariable Long id) {
        return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User update successfully", userService.putUser(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "User delete successfully", null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String loginId = user.getLoginId();
        String password = EncryptionUtil.encrypt(user.getPassword());

        User user1 = userService.validateUser(loginId, password);
        if (user1 != null) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "Login successful!", user1);
        } else {
            return  ApiResponseUtil.buildApiResponse("error", HttpStatus.UNAUTHORIZED.value(), "Invalid username or password", null);
        }

    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody User user) {
        User user1 = userService.changePassword(user.getLoginId(), user.getPassword());
        if (user1 != null) {
            return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "Password change successfully", user1);
        } else {
            return ApiResponseUtil.buildApiResponse("error", HttpStatus.NOT_FOUND.value(), "User not found", null);
        }
    }

}
