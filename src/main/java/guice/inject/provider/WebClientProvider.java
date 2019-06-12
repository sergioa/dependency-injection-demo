package guice.inject.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import guice.inject.enums.Scheme;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static java.nio.charset.StandardCharsets.UTF_8;

public class WebClientProvider implements Provider<WebClient> {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private Scheme scheme;
    private String key;
    private String secret;


    @Inject
    public WebClientProvider(@Named("scheme") Scheme scheme,
                             @Named("key") String key,
                             @Named("secret") String secret) {
        this.scheme = scheme;
        this.key = key;
        this.secret = secret;
    }


    public WebClient get() {
        return switch (scheme) {
            case BASIC -> basic(key, secret);
            case MUTUAL -> mutual(key, secret);
        };
    }


    private WebClient basic(String username, String password) {
        String base64Encoded = Base64Utils.encodeToString((username + ":" + password).getBytes(UTF_8));
        return WebClient.builder()
                .defaultHeader(AUTHORIZATION_HEADER, "Basic " + base64Encoded)
                .build();
    }


    private WebClient mutual(String certificate, String privateKey) {

        final SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .secure(builder -> builder.sslContext(sslContextBuilder))
                ))
                .build();
    }

}
