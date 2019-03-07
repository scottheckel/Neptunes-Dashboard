package macro.dashboard.neptunes.backend

import com.mashape.unirest.http.Unirest
import macro.dashboard.neptunes.config.Config.Companion.CONFIG
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.ProxyAuthenticationStrategy
import org.apache.logging.log4j.LogManager
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import khttp.post as httpPost

/**
 * Created by Macro303 on 2019-Feb-26.
 */
object RESTClient {
	private val LOGGER = LogManager.getLogger(RESTClient::class.java)
	private val HEADERS = mapOf(
		"Content-Type" to "application/json",
		"User-Agent" to "Neptune's Dashboard"
	)

	fun postRequest(url: String, gameID: Long, code: String): Map<String, Any?> {
		return if (CONFIG.proxy == null)
			khttpRequest(url = url, gameID = gameID, code = code)
		else {
			unirestRequest(url = url, gameID = gameID, code = code)
		}
	}

	private fun khttpRequest(url: String, gameID: Long, code: String): Map<String, Any?> {
		val response = httpPost(
			url = url,
			headers = HEADERS,
			data = mapOf(
				"api_version" to 0.1,
				"game_number" to gameID,
				"code" to code
			)
		)
		LOGGER.info("Status: ${response.statusCode}")
		return mapOf(
			"Code" to response.statusCode,
			"Response" to response.jsonObject["scanning_data"].toString()
		)
	}

	private fun unirestRequest(url: String, gameID: Long, code: String): Map<String, Any>{
		Unirest.setProxy(HttpHost(CONFIG.proxyHostname, CONFIG.proxyPort!!))
		val boundary = "===${System.currentTimeMillis()}==="
		val response = Unirest.post(url)
			.header("content-type", "multipart/form-data; boundary=$boundary")
			.header("User-Agent", "Neptune's Dashboard")
			.body("--$boundary\r\nContent-Disposition: form-data; name=\"api_version\"\r\n\r\n0.1\r\n--$boundary\r\nContent-Disposition: form-data; name=\"game_number\"\r\n\r\n$gameID\r\n--$boundary\r\nContent-Disposition: form-data; name=\"code\"\r\n\r\n$code\r\n--$boundary--")
			.asJson()
		LOGGER.info("Status: ${response.status}")
		return mapOf(
			"Code" to response.status,
			"Response" to response.body.`object`["scanning_data"].toString()
		)
	}
}