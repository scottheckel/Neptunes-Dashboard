package macro.neptunes.config

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route
import macro.neptunes.NotImplementedException
import macro.neptunes.config.Config.Companion.CONFIG

/**
 * Created by Macro303 on 2019-Jan-25.
 */
internal object ConfigRouter {

	fun Route.settingRoutes() {
		route(path = "/settings") {
			get {
				call.respond(
					message = CONFIG,
					status = HttpStatusCode.OK
				)
			}
			put {
				throw NotImplementedException()
			}
		}
	}
}