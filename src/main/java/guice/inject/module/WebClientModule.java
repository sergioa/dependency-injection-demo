package guice.inject.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import guice.inject.provider.WebClientProvider;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientModule extends AbstractModule {

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("scheme")).to("");
        bindConstant().annotatedWith(Names.named("key")).to("");
        bindConstant().annotatedWith(Names.named("secret")).to("");

        bind(WebClient.class).toProvider(WebClientProvider.class);
    }

}
