package macro.neptunes.game

import macro.neptunes.Util
import macro.neptunes.Util.toJavaDateTime
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

/**
 * Created by Macro303 on 2019-Feb-25.
 */
object TurnTable : Table(name = "Game_Turns") {
	private val gameCol: Column<EntityID<Long>> = reference(
		name = "gameID",
		foreign = GameTable,
		onUpdate = ReferenceOption.CASCADE,
		onDelete = ReferenceOption.CASCADE
	)
	private val startTimeCol: Column<DateTime> = datetime(name = "startTime")
	private val productionCol: Column<Int> = integer(name = "production")
	private val isGameOverCol: Column<Boolean> = bool(name = "isGameOver")
	private val isPausedCol: Column<Boolean> = bool(name = "isPaused")
	private val isStartedCol: Column<Boolean> = bool(name = "isStarted")
	private val productionCounterCol: Column<Int> = integer(name = "productionCounter")
	private val tickCol: Column<Int> = integer(name = "tick")
	private val tickFragmentCol: Column<Int> = integer(name = "tickFragment")
	private val tradeScannedCol: Column<Int> = integer(name = "tradeScanned")
	private val warCol: Column<Int> = integer(name = "war")
	private val turnBasedTimeoutCol: Column<Int> = integer(name = "turnBasedTimeout")

	init {
		Util.query {
			uniqueIndex(gameCol, tickCol)
			SchemaUtils.create(this)
		}
	}

	fun select(ID: Long, tick: Int): Turn? = Util.query {
		select {
			gameCol eq ID and (tickCol eq tick)
		}.map {
			it.parse()
		}.sorted().firstOrNull()
	}

	fun search(ID: Long): List<Turn> = Util.query {
		select {
			gameCol eq ID
		}.map {
			it.parse()
		}.sorted()
	}

	private fun ResultRow.parse() = Turn(
		game = GameTable.select(ID = this[gameCol].value)!!,
		startTime = this[startTimeCol].toJavaDateTime(),
		production = this[productionCol],
		isGameOver = this[isGameOverCol],
		isPaused = this[isPausedCol],
		isStarted = this[isStartedCol],
		productionCounter = this[productionCounterCol],
		tick = this[tickCol],
		tickFragment = this[tickFragmentCol],
		tradeScanned = this[tradeScannedCol],
		war = this[warCol],
		turnBasedTimeout = this[turnBasedTimeoutCol]
	)
}