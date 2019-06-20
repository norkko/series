package net.series.rest.api.account.config.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.series.rest.api.account.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static net.series.rest.api.account.config.Constants.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            logger.info("Attempt authentication");
            Account account = new ObjectMapper().readValue(request.getInputStream(), Account.class);
            return authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword())));
        } catch (IOException e) {
            logger.error("Attempting authentication failed");
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        try {
            ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS);
            String token = Jwts.builder()
                    .setSubject(((User) authResult
                            .getPrincipal())
                            .getUsername())
                    .setExpiration(Date.from(expirationTimeUTC.toInstant()))
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
            response.getWriter().write(token);
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        } catch (Exception e) {
            logger.warn("warning " + e);
        }
    }
}