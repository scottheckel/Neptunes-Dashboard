package macro.dashboard.neptunes.technology

import macro.dashboard.neptunes.Util
import macro.dashboard.neptunes.backend.TechUpdate
import macro.dashboard.neptunes.player.PlayerTable
import macro.dashboard.neptunes.player.TurnTable
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

/**
 * Created by Macro303 on 2019-Mar-08.
 */
object TechnologyTable : IntIdTable(name = "Technology") {
	private val turnCol = reference(
		name = "turnID",
		foreign = PlayerTable,
		onUpdate = ReferenceOption.CASCADE,
		onDelete = ReferenceOption.CASCADE
	)
	private val nameCol = text(name = "name")
	private val valueCol = double(name = "value")
	private val levelCol = integer(name = "level")

	private val LOGGER = LogManager.getLogger(TurnTable::class.java)

	init {
		Util.query {
			uniqueIndex(turnCol, nameCol)
			SchemaUtils.create(this)
		}
	}

	fun select(ID: Int): Technology? = Util.query {
		select {
			id eq ID
		}.map {
			it.parse()
		}.sorted().firstOrNull()
	}

	fun searchByTurn(turnID: Int): List<Technology> = Util.query {
		select {
			turnCol eq turnID
		}.map {
			it.parse()
		}.sorted()
	}

	fun insert(turnID: Int, name: String, update: TechUpdate): Boolean = Util.query {
		try {
			insert {
				it[turnCol] = EntityID(id = turnID, table = TurnTable)
				it[nameCol] = name
				it[valueCol] = update.value
				it[levelCol] = update.level
			}
			true
		} catch (esqle: ExposedSQLException) {
			false
		}
	}

	private fun ResultRow.parse(): Technology = Technology(
		ID = this[id].value,
		turnID = this[turnCol].value,
		name = this[nameCol],
		value = this[valueCol],
		level = this[levelCol]
	)
}