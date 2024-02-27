package Base.APITemplete.controller;

import Base.APITemplete.model.AuthRequest;
import Base.APITemplete.util.ApiResponseUtil;
import Base.APITemplete.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class SecurityTokenController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (AuthenticationException ex) {
            return ApiResponseUtil.buildApiResponse("error", HttpStatus.UNAUTHORIZED.value(), "Invalid username/password", null);
        }

        return ApiResponseUtil.buildApiResponse("success", HttpStatus.OK.value(), "Token generated successfully", jwtTokenUtil.generateToken(authRequest.getUserName()));
    }
}
