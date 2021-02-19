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
	$lasers_btn = $_GET["lasers_btn"];
	$mobile_phone_btn = $_GET["mobile_phone_btn"];
	$office_door_btn = $_GET["office_door_btn"];
	$desk_cabinet_btn = $_GET["desk_cabinet_btn"];
	$bookcage_cabinet_btn = $_GET["bookcage_cabinet_btn"];
	$bansky_painting_btn = $_GET["bansky_painting_btn"];
	$frame_rfid_btn = $_GET["frame_rfid_btn"];
	$control_room_door_btn = $_GET["control_room_door_btn"];
	$vault_door_btn = $_GET["vault_door_btn"];
	$ventilation_btn = $_GET["ventilation_btn"];
	$money_drop_btn = $_GET["money_drop_btn"];
	$panel_numbers_btn = $_GET["panel_numbers_btn"];
	$console_large_buttons_btn = $_GET["console_large_buttons_btn"];
	$console_metal_buttons_btn = $_GET["console_metal_buttons_btn"];
	$passage_btn = $_GET["passage_btn"];
	$exit_btn = $_GET["exit_btn"];
	$reset_btn = $_GET["reset_btn"];
	
	$sql = "UPDATE mission SET 
			lasers_btn = '$lasers_btn', 
			mobile_phone_btn = '$mobile_phone_btn', 
			office_door_btn = '$office_door_btn', 
			desk_cabinet_btn = '$desk_cabinet_btn', 
			bookcage_cabinet_btn = '$bookcage_cabinet_btn', 
			bansky_painting_btn = '$bansky_painting_btn', 
			frame_rfid_btn = '$frame_rfid_btn', 
			control_room_door_btn = '$control_room_door_btn', 
			vault_door_btn = '$vault_door_btn', 
			ventilation_btn = '$ventilation_btn', 
			money_drop_btn = '$money_drop_btn', 
			panel_numbers_btn = '$panel_numbers_btn', 
			console_large_buttons_btn = '$console_large_buttons_btn', 
			console_metal_buttons_btn = '$console_metal_buttons_btn', 
			passage_btn = '$passage_btn',  
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