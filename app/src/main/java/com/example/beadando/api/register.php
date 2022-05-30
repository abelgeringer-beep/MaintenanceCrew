<?php

require_once 'include/DB_Functions.php';

    $response = array();

    if($_SERVER['REQUEST_METHOD'] != 'POST'){
        $response['error'] = true;
        $response['message'] = "Invalid Request";
        return;
    }

    if(!isset($_POST['username']) && !isset($_POST['password'])){
        $response['error'] = true;
        $response['message'] = "Required fields are missing";
        return;
    }

    $db = new DB_Functions();

    if(!$db->createUser($_POST['username'], $_POST['password'])){
        $response['error'] = true;
        $response['message'] = "Some error occured please try again";
        return;
    }
    
    $response['error'] = false;
    $response['message'] = "User registered successfully";

    echo json_encode($response);
?>