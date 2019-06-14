package guice.inject

import com.google.inject.Guice
import guice.inject.enums.Scheme
import guice.inject.module.WebClientModule
import org.springframework.web.reactive.function.client.WebClient

object Application {

    @JvmStatic
    fun main(args: Array<String>) {

        val injector = Guice.createInjector(WebClientModule(Scheme.MUTUAL, "username", "password"))
        val client = injector.getInstance<WebClient>(WebClient::class.java)
    }
}
