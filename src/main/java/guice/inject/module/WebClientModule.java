package guice.inject.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import guice.inject.enums.Scheme;
import guice.inject.provider.WebClientProvider;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientModule extends AbstractModule {

    private Scheme scheme;
    private String key;
    private String secret;


    public WebClientModule(Scheme scheme, String key, String secret) {
        this.scheme = scheme;
        this.key = key;
        this.secret = secret;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("scheme")).to(scheme);
        bindConstant().annotatedWith(Names.named("key")).to(key);
        bindConstant().annotatedWith(Names.named("secret")).to(secret);

        bind(WebClient.class).toProvider(WebClientProvider.class);
    }

}
