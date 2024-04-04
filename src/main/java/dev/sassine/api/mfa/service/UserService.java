package dev.sassine.api.mfa.service;

import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.sassine.api.mfa.domain.UserEntity;
import dev.sassine.api.mfa.dto.QRCodeDTO;
import dev.sassine.api.mfa.dto.UserDTO;
import dev.sassine.api.mfa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final MFAService mfaService;

    public UserService(UserRepository userRepository, MFAService mfaService) {
        this.userRepository = userRepository;
        this.mfaService = mfaService;
    }

    public QRCodeDTO create(UserDTO user) throws QrGenerationException {
        var userEntity = userRepository.save(UserEntity.builder()
                .username(user.user())
                .password(user.password())
                .secret(mfaService.generateSecretKey()).build());

        log.info("User %s created and new secret token genereted %s".formatted(userEntity.getUsername(),userEntity.getSecret()));

        return new QRCodeDTO(userEntity.getId(),
                "data:image/png;base64,%s".formatted(
                        mfaService.generateQrCodeUrl(
                                userEntity.getUsername(),
                                userEntity.getSecret()
                        )
                )
        );
    }
}
