package dev.sassine.api.mfa.service;

import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.sassine.api.mfa.dto.VerifyCodeDTO;
import dev.sassine.api.mfa.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Base64;

@Service
public class MFAService {

    private final UserRepository userRepository;

    public MFAService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateSecretKey() {
        return new DefaultSecretGenerator().generate();
    }

    public String generateQrCodeUrl(String username, String secret) throws QrGenerationException {
        var qrCode = new ZxingPngQrGenerator().generate(new QrData.Builder()
                .label(username)
                .secret(secret)
                .issuer("DemoMFA SimpleProject")
                .algorithm(HashingAlgorithm.SHA256)
                .digits(6)
                .period(30)
                .build());
        return Base64.getEncoder().encodeToString(qrCode);
    }

    public void verifyCode(Long userId, VerifyCodeDTO vc) throws AuthenticationException {
        var verifier = new DefaultCodeVerifier(
                new DefaultCodeGenerator(HashingAlgorithm.SHA1),
                new SystemTimeProvider()
        );
        var user = userRepository.findById(userId);
        if(!(verifier.isValidCode(user.get().getSecret(), vc.code()))){
            throw new AuthenticationException("Code not valid.");
        }
    }


}
