package net.series.rest.api.account.config.filter;

import io.jsonwebtoken.Jwts;
import net.series.rest.api.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static net.series.rest.api.account.config.Constants.*;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    private final AccountService accountService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, AccountService accountService ) {
        super(authenticationManager);
        this.accountService = accountService;
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        try {
            String token = request.getHeader(HEADER_STRING);
            if (token == null) {
                logger.info("token null");
                return null;
            }

            String username = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            logger.info("" + token);
            UserDetails account = accountService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
        } catch (Exception e) {
            logger.error("err " + e);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            String header = request.getHeader(HEADER_STRING);
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuth = getAuthenticationToken(request);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.warn("err " + e);
        }

    }

}