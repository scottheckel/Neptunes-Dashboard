<!DOCTYPE html>
<html lang="en">
<head>
	<title>Neptune's Dashboard</title>
	<meta charset="utf-8">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<link href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" rel="stylesheet" type="text/css">
	<link href="https://cdn.datatables.net/1.10.18/css/dataTables.semanticui.css" rel="stylesheet" type="text/css"/>
	<link href="/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="navbar"></div>
<div class="ui container">
	<div class="ui inverted container segment opacity">
		<h1 class="ui center aligned header">${name}</h1>
	</div>
	<div class="ui four stackable cards">
		<div class="ui orange card opacity">
			<div class="content">
				<div class="header">Total Stars</div>
				<div class="description"><b>Total:</b> ${totalStars}</div>
				<div class="extra content"><b>Average:</b> ${totalStars/players?size}</div>
			</div>
		</div>
		<div class="ui orange card opacity">
			<div class="content">
				<div class="header">Total Ships</div>
				<div class="description"><b>Total:</b> ${totalShips}</div>
				<div class="extra content"><b>Average:</b> ${totalShips/players?size}</div>
			</div>
		</div>
		<div class="ui orange card opacity">
			<div class="content">
				<div class="header">Total Fleet</div>
				<div class="description"><b>Total:</b> ${totalFleet}</div>
				<div class="extra content"><b>Average:</b> ${totalFleet/players?size}</div>
			</div>
		</div>
		<div class="ui orange card opacity">
			<div class="content">
				<div class="header">Total Economy</div>
				<div class="description"><b>Total:</b> ${totalEconomy}</div>
				<div class="extra content"><b>Average:</b> ${totalEconomy/players?size}</div>
			</div>
		</div>
		<div class="ui orange card opacity">
			<div class="content">
				<div class="header">Total Industry</div>
				<div class="description"><b>Total:</b> ${totalIndustry}</div>
				<div class="extra content"><b>Average:</b> ${totalIndustry/players?size}</div>
			</div>
		</div>
		<div class="ui orange card opacity">
			<div class="content">
				<div class="header">Total Science</div>
				<div class="description"><b>Total:</b> ${totalScience}</div>
				<div class="extra content"><b>Average:</b> ${totalScience/players?size}</div>
			</div>
		</div>
		<#list players as player>
			<div class="ui orange card opacity">
				<div class="content">
					<div class="header">${player.alias} (${player.name!"Unknown"})</div>
					<div class="description">
						<b>Stars:</b> ${player.stars}</br>
						<b>Ships:</b> ${player.ships}
					</div>
				</div>
			</div>
		</#list>
	</div>
	<div class="ui inverted container segment opacity">
		<div class="ui center aligned large header">Star Distribution</div>
		<div class="chart-container">
			<canvas height="400" id="winPie" width="600"></canvas>
		</div>
	</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js" type="text/javascript"></script>
<script src="https://cdn.datatables.net/1.10.18/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="https://cdn.datatables.net/1.10.18/js/dataTables.semanticui.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/patternomaly@1.3.2/dist/patternomaly.min.js" type="text/javascript"></script>
<script src="/script.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#navbar").load("/navbar.html");
	getTeamStars("${name}");
});
</script>
</body>
</html>