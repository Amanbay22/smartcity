package security;

import entities.Profile;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;


@ApplicationScoped
public class JWTService {

    private List<String> validJWTTokens = new ArrayList();
    private String secret;
    private int JWT_TOKEN_VALIDITY = 100000;
    public String generateJWTToken(Profile profile) {
        secret = "secret";
        String encodedString = Base64.getEncoder().encodeToString(secret.getBytes());
        String token = Jwts.builder()
                .claim("email", profile.getEmail())
                .claim("password", profile.getPassword())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, encodedString ).compact();
        this.validJWTTokens.add(token);
        return token;
    }

    public String valid(String token) {
        if (!this.validJWTTokens.contains(token)) {
            return "not valid";
        }

        Base64.Decoder decoder = Base64.getDecoder();
        String[] chunks = token.split("\\.");
        String header = new String(decoder.decode(chunks[0]));
        return new String(decoder.decode(chunks[1]));
    }

    public void removeToken(String token) {
        this.validJWTTokens.remove(token);
    }
}
