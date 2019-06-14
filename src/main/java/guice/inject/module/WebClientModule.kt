package guice.inject.module

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import guice.inject.enums.Scheme
import guice.inject.provider.WebClientProvider
import org.springframework.web.reactive.function.client.WebClient

class WebClientModule(private val scheme: Scheme, private val key: String, private val secret: String) : AbstractModule() {

    override fun configure() {
        bindConstant().annotatedWith(Names.named("scheme")).to(scheme)
        bindConstant().annotatedWith(Names.named("key")).to(key)
        bindConstant().annotatedWith(Names.named("secret")).to(secret)

        bind<WebClient>(WebClient::class.java).toProvider(WebClientProvider::class.java)
    }

}
