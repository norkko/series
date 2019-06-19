package net.series.rest.api.account.config.filter;

import io.jsonwebtoken.Jwts;
import net.series.rest.api.account.service.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.series.rest.api.account.config.Constants.*;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final AccountService accountService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, AccountService accountService ) {
        super(authenticationManager);
        this.accountService = accountService;
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken (HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null) {
            return null;
        }
        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        UserDetails account = accountService.loadUserByUsername(username);
        return username != null ? new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities()) : null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuth = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
        chain.doFilter(request, response);
    }

}