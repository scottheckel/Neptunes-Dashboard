package macro.neptunes.game

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import macro.neptunes.DataNotFoundException
import macro.neptunes.NotImplementedException
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2018-Nov-16.
 */
internal object GameController {
	private val LOGGER = LogManager.getLogger(GameController::class.java)

	fun ApplicationCall.parseParam(): Game {
		val ID = parameters["ID"]
		val game = GameTable.select(ID = ID?.toLongOrNull() ?: 1.toLong())
		if (ID == null || game == null)
			throw DataNotFoundException(type = "Game", field = "ID", value = ID)
		return game
	}

	fun Route.gameRoutes() {
		route(path = "/game") {
			get(path = "/latest") {
				val game = GameTable.search().firstOrNull()
					?: throw DataNotFoundException(type = "Game", field = "ID", value = null)
				call.respond(
					message = game.toOutput(),
					status = HttpStatusCode.OK
				)
			}
			route(path = "/{ID}") {
				get{
					call.respond(
						message = call.parseParam().toOutput(),
						status = HttpStatusCode.OK
					)
				}
				get(path = "/players"){
					throw NotImplementedException()
				}
				get(path = "/teams"){
					throw NotImplementedException()
				}
			}
		}
	}
}