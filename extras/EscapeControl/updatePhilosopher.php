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
	$coins_prop_btn = $_GET["coins_prop_btn"];
	$harp_btn = $_GET["harp_btn"];
	$passage_btn = $_GET["passage_btn"];
	$knight_video_btn = $_GET["knight_video_btn"];
	$holding_keys_door_btn = $_GET["holding_keys_door_btn"];
	$books_open_btn = $_GET["books_open_btn"];
	$books_close_btn = $_GET["books_close_btn"];
	$bookcase_door_btn = $_GET["bookcase_door_btn"];
	$mirror_btn = $_GET["mirror_btn"];
	$exit_btn = $_GET["exit_btn"];
	$reset_btn = $_GET["reset_btn"];
	
	$sql = "UPDATE philosopher SET 
			coins_prop_btn = '$coins_prop_btn', 
			harp_btn = '$harp_btn', 
			passage_btn = '$passage_btn', 
			knight_video_btn = '$knight_video_btn', 
			holding_keys_door_btn = '$holding_keys_door_btn', 
			books_open_btn = '$books_open_btn', 
			books_close_btn = '$books_close_btn', 
			bookcase_door_btn = '$bookcase_door_btn', 
			mirror_btn = '$mirror_btn', 
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