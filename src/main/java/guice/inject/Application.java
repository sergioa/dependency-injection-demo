package guice.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.inject.enums.Scheme;
import guice.inject.module.WebClientModule;
import org.springframework.web.reactive.function.client.WebClient;

public class Application {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new WebClientModule(Scheme.MUTUAL, "username", "password"));
        WebClient client = injector.getInstance(WebClient.class);
    }
}
