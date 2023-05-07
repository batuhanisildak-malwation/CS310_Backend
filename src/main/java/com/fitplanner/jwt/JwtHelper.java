package com.fitplanner.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fitplanner.model.RefreshToken;
import com.fitplanner.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@Log4j2
public class JwtHelper {
    static final String issuer = "FitPlanner";

    private long accessTokenExpirationMs;
    private long refreshTokenExpirationMs;

    private Algorithm accessTokenAlgorithm;
    private Algorithm refreshTokenAlgorithm;
    private JWTVerifier accessTokenVerifier;
    private JWTVerifier refreshTokenVerifier;

    public JwtHelper(@Value("${com.fitplanner.accessTokenSecret}") String accessTokenSecret,
            @Value("${com.fitplanner.refreshTokenSecret}") String refreshTokenSecret,
            @Value("${com.fitplanner.refreshTokenExpirationDays}") int refreshTokenExpirationDays,
            @Value("${com.fitplanner.accessTokenExpirationMinutes}") int accessTokenExpirationMinutes) {
        accessTokenExpirationMs = (long) accessTokenExpirationMinutes * 60 * 1000; // cuz its in minutes - convert to ms
                                                                                   // - Batuhan
        refreshTokenExpirationMs = (long) refreshTokenExpirationDays * 24 * 60 * 60 *
                1000; // cuz its in days, convert to ms - Batuhan
        accessTokenAlgorithm = Algorithm.HMAC512(accessTokenSecret); // HMAC512 is a hashing algorithm - Batuhan
        refreshTokenAlgorithm = Algorithm.HMAC512(refreshTokenSecret); // HMAC512 is a hashing algorithm - Batuhan
        accessTokenVerifier = JWT.require(accessTokenAlgorithm)
                .withIssuer(issuer)
                .build(); // build the verifier - Batuhan
        refreshTokenVerifier = JWT.require(refreshTokenAlgorithm)
                .withIssuer(issuer)
                .build(); // build the verifier - Batuhan
    }

    // generate access token - Batuhan
    public String generateAccessToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + accessTokenExpirationMs))
                .sign(accessTokenAlgorithm);
    }

    // generate refresh token - Batuhan
    public String generateRefreshToken(User user, RefreshToken refreshToken) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId())
                .withClaim("tokenId", refreshToken.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((new Date()).getTime() + refreshTokenExpirationMs))
                .sign(refreshTokenAlgorithm);
    }

    // decode access token - Batuhan
    private Optional<DecodedJWT> decodeAccessToken(String token) {
        try {
            return Optional.of(accessTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            log.error("invalid access token", e);
        }
        return Optional.empty();
    }

    // decode refresh token - Batuhan
    private Optional<DecodedJWT> decodeRefreshToken(String token) {
        try {
            return Optional.of(refreshTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            log.error("invalid refresh token", e);
        }
        return Optional.empty();
    }

    // validate access token - Batuhan
    public boolean validateAccessToken(String token) {
        return decodeAccessToken(token).isPresent();
    }

    // validate refresh token - Batuhan
    public boolean validateRefreshToken(String token) {
        return decodeRefreshToken(token).isPresent();
    }

    // get user id from access token - Batuhan
    public String getUserIdFromAccessToken(String token) {
        return decodeAccessToken(token).get().getSubject();
    }

    // get user id from refresh token - Batuhan
    public String getUserIdFromRefreshToken(String token) {
        return decodeRefreshToken(token).get().getSubject();
    }

    // get token id from refresh token - Batuhan
    public String getTokenIdFromRefreshToken(String token) {
        return decodeRefreshToken(token).get().getClaim("tokenId").asString();
    }
}