package com._52org.gardenoftheheart.application.garden.service;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.dto.GardenLoginResponseDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenRequestDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenSignUpResponseDTO;
import com._52org.gardenoftheheart.application.garden.error.GardenErrorCode;
import com._52org.gardenoftheheart.application.garden.error.GardenException;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import com._52org.gardenoftheheart.security.service.CustomUserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GardenServiceTest {

    @InjectMocks
    private GardenService target;

    @Mock
    private CustomUserDetailService userDetailService;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private GardenRepository gardenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final String gardenName = "재이";
    private final String password = "제이름은요";
    private final GardenRequestDTO gardenRequestDTO = new GardenRequestDTO(gardenName, password);

    @BeforeEach
    public void setUp() {

        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void 텃밭생성실패_이미존재함() {

        // given
        doReturn(Optional.of(Garden.builder().build())).when(gardenRepository).findByGardenName(gardenName);

        // when
        final GardenException result = assertThrows(GardenException.class, () -> target.createGarden(gardenRequestDTO));

        // then
        assertThat(result.getErrorCode()).isEqualTo(GardenErrorCode.DUPLICATED_GARDENNAME);

    }

    @Test
    public void 텃밭생성성공() {

        // given
        doReturn(Optional.empty()).when(gardenRepository).findByGardenName(gardenName);
        doReturn(garden()).when(gardenRepository).save(any(Garden.class));

        // when
        final GardenSignUpResponseDTO result = target.createGarden(gardenRequestDTO);

        // then
//        assertThat(result.getId()).isNotNull();
        assertThat(result.getUuid()).isNotNull();

        // verify
        verify(gardenRepository, times(1)).findByGardenName(gardenName);
        verify(gardenRepository, times(1)).save(any(Garden.class));

    }

    @Test
    public void 로그인실패_gardenName이존재하지않음() {

        // given
        doReturn(Optional.empty()).when(gardenRepository).findByGardenName(gardenName);

        // when
        final GardenException result = assertThrows(GardenException.class, () -> target.login(gardenRequestDTO));

        // then
        assertThat(result.getErrorCode()).isEqualTo(GardenErrorCode.NON_EXISTENT_GARDEN);

    }

    @Test
    public void 로그인실패_password가틀림() {

        // given
        doReturn(Optional.of(garden())).when(gardenRepository).findByGardenName(gardenName);

        // when
        final GardenException result = assertThrows(GardenException.class, () -> target.login(gardenRequestDTO));

        // then
        assertThat(result.getErrorCode()).isEqualTo(GardenErrorCode.INVALID_PASSWORD);

    }

    @Test
    public void 로그인성공() {

        // given
        doReturn(Optional.of(garden())).when(gardenRepository).findByGardenName(gardenName);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(gardenRequestDTO.getGardenName(), gardenRequestDTO.getPassword()));
//        doReturn(User.builder()
//                .username(garden().getUsername())
//                .password(garden().getPassword())
//                .roles(garden().getRoles().toArray(new String[0]))
//                .build())
//                .when(userDetailService).loadUserByUsername(gardenName);

        // when
        final GardenLoginResponseDTO result = target.login(gardenRequestDTO);
//        final UserDetails result = userDetailService.loadUserByUsername(gardenName);

        // then
        assertThat(result).isNotNull();
//        assertThat(result.getTokenDTO()).isNotNull();
//        assertThat(result.getUuid()).isNotNull();
//        assertThat(result.getTokenDTO().getGrantType()).isEqualTo("Bearer ");

        // verify
//        verify(gardenRepository, times(1)).findByGardenName(gardenName);
//        verify(gardenRepository, times(1)).save(any(Garden.class));

    }

    private Garden garden() {

        System.out.println(password);
        System.out.println(passwordEncoder.encode(password));

        return Garden.builder()
                .gardenName(gardenName)
                .password(passwordEncoder.encode(password))
                .uuid(UUID.randomUUID().toString())
                .build();

    }

}
