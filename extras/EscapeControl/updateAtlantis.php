<?php
	$servername = "localhost";
	$username = "arduino";
	$password = "1234";
	$dbname = "escapecontrol";

	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	  die("Connection failed: " . $conn->connect_error);
	}

	$id = $_GET["id"];
	$tritons_key = $_GET["tritons_key"];
	$room_door_1_2 = $_GET["room_door_1_2"];
	$holy_molly = $_GET["holy_molly"];
	$poseidons_chest = $_GET["poseidons_chest"];
	$hexagon_cabinets = $_GET["hexagon_cabinets"];
	$waterfall_btn = $_GET["waterfall_btn"];
	$tritons_key_btn = $_GET["tritons_key_btn"];
	$column1_btn = $_GET["column1_btn"];
	$column2_btn = $_GET["column2_btn"];
	$column3_btn = $_GET["column3_btn"];
	$room_door_1_2_btn = $_GET["room_door_1_2_btn"];
	$holy_molly_btn = $_GET["holy_molly_btn"];
	$poseidon_btn = $_GET["poseidon_btn"];
	$hexagon_pattern_btn = $_GET["hexagon_pattern_btn"];
	$hexagon_cabinets_btn = $_GET["hexagon_cabinets_btn"];
	$trident_unlock_btn = $_GET["trident_unlock_btn"];
	$exit_btn = $_GET["exit_btn"];
	$reset_btn = $_GET["reset_btn"];
	
	$sql = "UPDATE atlantis SET 
			tritons_key = '$tritons_key', 
			room_door_1_2 = '$room_door_1_2', 
			holy_molly = '$holy_molly', 
			poseidons_chest = '$poseidons_chest', 
			hexagon_cabinets = '$hexagon_cabinets', 
			waterfall_btn = '$waterfall_btn', 
			tritons_key_btn = '$tritons_key_btn', 
			column1_btn = '$column1_btn', 
			column2_btn = '$column2_btn', 
			column3_btn = '$column3_btn', 
			room_door_1_2_btn = '$room_door_1_2_btn', 
			holy_molly_btn = '$holy_molly_btn', 
			poseidon_btn = '$poseidon_btn', 
			hexagon_pattern_btn = '$hexagon_pattern_btn', 
			hexagon_cabinets_btn = '$hexagon_cabinets_btn', 
			trident_unlock_btn = '$trident_unlock_btn', 
			exit_btn = '$exit_btn', 
			reset_btn = '$reset_btn' 
			WHERE id = $id";

	if ($conn->query($sql) === TRUE) {
	  echo "Record updated successfully";
	} else {
	  echo "Error updating record: " . $conn->error;
	}

	$conn->close();

?>