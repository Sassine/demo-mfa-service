package dev.sassine.api.mfa.controller;

import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.sassine.api.mfa.dto.QRCodeDTO;
import dev.sassine.api.mfa.dto.UserDTO;
import dev.sassine.api.mfa.dto.VerifyCodeDTO;
import dev.sassine.api.mfa.service.MFAService;
import dev.sassine.api.mfa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final MFAService mfaService;
    private final UserService userService;

    public UserController(MFAService mfaService, UserService userService) {
        this.mfaService = mfaService;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QRCodeDTO create(@RequestBody UserDTO user) throws QrGenerationException {
        return userService.create(user);
    }

    @PostMapping("/{userId}/verify")
    public void verify(@PathVariable Long userId, @RequestBody VerifyCodeDTO vc) throws AuthenticationException {
        mfaService.verifyCode(userId, vc);
    }

}
