package com.a504.userdemo.service.security;

import com.a504.userdemo.advice.exception.CEmailLoginFailedCException;
import com.a504.userdemo.advice.exception.CRefreshTokenException;
import com.a504.userdemo.advice.exception.CUserNotFoundException;
import com.a504.userdemo.config.security.JwtProvider;
import com.a504.userdemo.dto.jwt.TokenDto;
import com.a504.userdemo.dto.jwt.TokenRequestDto;
import com.a504.userdemo.dto.user.UserLoginRequestDto;
import com.a504.userdemo.dto.user.UserSignupRequestDto;
import com.a504.userdemo.entity.security.RefreshToken;
import com.a504.userdemo.entity.user.User;
import com.a504.userdemo.repository.RefreshTokenJpaRepo;
import com.a504.userdemo.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenJpaRepo tokenJpaRepo;

    @Transactional
    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {
        //  회원 정보가 존재하는지 확인
        User user = userJpaRepo.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(CEmailLoginFailedCException::new);

        //  회원 패스워드 일치 여부 확인
        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedCException();

        //  AccessToken, RefreshToken 발급
        TokenDto tokenDto = jwtProvider.createTokenDto(user.getUserId(), user.getRoles());

        //  RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(user.getUserId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenJpaRepo.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public Long signup(UserSignupRequestDto userSignupDto) {
        if(userJpaRepo.findByEmail(userSignupDto.getEmail()).isPresent())
            throw new CEmailLoginFailedCException();
        return userJpaRepo.save(userSignupDto.toEntity(passwordEncoder)).getUserId();
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        //  만료된 refresh token 에러
        if(!jwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            System.out.println("1!!!!");
            throw new CRefreshTokenException();
        }

        //  AccessToken에서 Username (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        System.out.println("authentication.getName() = " + authentication.getName());

        //  user pk로 유저 검색 / repo에 저장된 Refresh Token이 없음
        System.out.println("2!!!!");
        User user = userJpaRepo.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);
        System.out.println("3!!!!");
        RefreshToken refreshToken = tokenJpaRepo.findByKey(user.getUserId())
                .orElseThrow(CRefreshTokenException::new);

        System.out.println("4!!!!");
        //  리프레시 토큰 불일치 에러
        if(!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        //  AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken = jwtProvider.createTokenDto(user.getUserId(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenJpaRepo.save(updateRefreshToken);

        return newCreatedToken;
    }
}
