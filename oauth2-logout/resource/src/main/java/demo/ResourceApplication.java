package demo;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableResourceServer
public class ResourceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceApplication.class);

	@Autowired
    private TokenStore tokenStore;
	
	@RequestMapping("/")
	public Message home(OAuth2Authentication authentication) {
		final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        
		LOGGER.debug(accessToken.getValue());
		
		return new Message("Hello World");
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(ResourceApplication.class, args);
	}

}

class Message {
	private String id = UUID.randomUUID().toString();
	private String content;

	Message() {
	}

	public Message(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
