<?php

require_once 'include/DB_Functions.php';

    $response = array();

    if($_SERVER['REQUEST_METHOD'] != 'POST'){
        $response['error'] = true;
        $response['message'] = "Invalid Request";
        echo json_encode($response);
        return;
    }

    if(!isset($_POST['username']) and !isset($_POST['password'])) {
        $response['error'] = true;
        $response['message'] = "Required fields are missing";
        echo json_encode($response);
        return;
    }

    $db = new DB_Functions();

    if (!$db->userLogin($_POST['username'], $_POST['password'])) {
        $response['error'] = true;
        $response['message'] = "Invalid username or password!";
        echo json_encode($response);
        return;
    }        

    $user = $db->getUserByUsername($_POST['username']);
    $user['error'] = false;

    echo json_encode($user);
?>