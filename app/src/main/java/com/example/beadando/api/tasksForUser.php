<?php

require_once 'include/DB_Functions.php';

$response = Array();

if($_SERVER['REQUEST_METHOD'] != 'POST'){
    $response['error'] = true;
    $response['message'] = "Invalid Request";
    echo json_encode($response);
    return;
}

if(!isset($_POST['user_id'])){
    $response['error'] = true;
    $response['message'] = "user_id is not set";
    echo json_encode($response);
    return;
}

$db = new DB_Functions();

$tasks = $db->getTasksForUser($_POST['user_id']);

$rows = array();

while($r = $tasks->fetch_assoc()){
    $rows[] = array('data' => $r);
}

$response = $rows;
echo json_encode($response);
?>