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
	$mirror_drawer = $_GET["mirror_drawer"];
	$mirror_cabinet = $_GET["mirror_cabinet"];
	$living_room_door = $_GET["living_room_door"];
	$kids_room_door = $_GET["kids_room_door"];
	$passage = $_GET["passage"];
	$window_doors = $_GET["window_doors"];
	$tarrot_combination_1_btn = $_GET["tarrot_combination_1_btn"];
	$tarrot_combination_2_btn = $_GET["tarrot_combination_2_btn"];
	$tarrot_combination_3_btn = $_GET["tarrot_combination_3_btn"];
	$radio_on_btn = $_GET["radio_on_btn"];
	$doll_btn = $_GET["doll_btn"];
	$shelf_1_btn = $_GET["shelf_1_btn"];
	$shelf_2_btn = $_GET["shelf_2_btn"];
	$window_doors_btn = $_GET["window_doors_btn"];
	$exit_btn = $_GET["exit_btn"];
	$reset_btn = $_GET["reset_btn"];
	
	$sql = "UPDATE haunted_mansion SET 
			mirror_drawer = '$mirror_drawer', 
			mirror_cabinet = '$mirror_cabinet', 
			living_room_door = '$living_room_door', 
			kids_room_door = '$kids_room_door', 
			passage = '$passage', 
			window_doors = '$window_doors', 
			tarrot_combination_1_btn = '$tarrot_combination_1_btn', 
			tarrot_combination_2_btn = '$tarrot_combination_2_btn', 
			tarrot_combination_3_btn = '$tarrot_combination_3_btn',  
			radio_on_btn = '$radio_on_btn', 
			doll_btn = '$doll_btn', 
			shelf_1_btn = '$shelf_1_btn', 
			shelf_2_btn = '$shelf_2_btn', 
			window_doors_btn = '$window_doors_btn', 
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