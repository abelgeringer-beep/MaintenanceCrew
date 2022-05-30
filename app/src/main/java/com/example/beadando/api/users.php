<?php

require_once 'include/DB_Functions.php';

if($_SERVER['REQUEST_METHOD'] != 'GET'){
    $response['error'] = true;
    $response['message'] = "Invalid Request";
    echo json_encode($response);
    return;
}

$db = new DB_Functions();

$user = $db->getUsers();

echo json_encode($user);

?>