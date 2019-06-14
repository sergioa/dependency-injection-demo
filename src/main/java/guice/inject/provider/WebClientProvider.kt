package guice.inject.provider

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.name.Named
import guice.inject.enums.Scheme
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.util.Base64Utils
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

import java.nio.charset.StandardCharsets.UTF_8

class WebClientProvider @Inject
constructor(@param:Named("scheme") private val scheme: Scheme,
            @param:Named("key") private val key: String,
            @param:Named("secret") private val secret: String) : Provider<WebClient> {


    override fun get(): WebClient {
        return when (scheme) {
            Scheme.BASIC -> basic(key, secret)
            Scheme.MUTUAL -> mutual(key, secret)
        }
    }


    private fun basic(username: String, password: String): WebClient {
        val base64Encoded = Base64Utils.encodeToString("$username:$password".toByteArray(UTF_8))
        return WebClient.builder()
                .defaultHeader(AUTHORIZATION_HEADER, "Basic $base64Encoded")
                .build()
    }


    private fun mutual(certificate: String, privateKey: String): WebClient {
        return WebClient.builder()
                .clientConnector(ReactorClientHttpConnector(
                        HttpClient.create()
                                .secure { builder -> builder.sslContext(SslContextBuilder.forClient()) }
                ))
                .build()
    }

    companion object {
        private val AUTHORIZATION_HEADER = "Authorization"
    }

}
