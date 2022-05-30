<?php
class DB_Functions {
 
    private $conn;
 
    function __construct() {
        require_once 'DB_Connect.php';
        $db = new DB_Connect();
        $this->conn = $db->connect();
    }

    function createUser($username, $password){
        try {
            $this->conn->query("INSERT INTO `users` (`user_id`, `qualification_id`, `password`, `username`, `status`, `registered_at`, `token`)
                                VALUES (NULL, NULL, '".md5($password)."', '".$username."', '', current_timestamp(), '');");
            return true;
        } catch (Exception $e) {
            echo $e->getMessage();
            return false;
        }
        return false;
    }

    function userLogin($username, $password){
        $result = $this->conn->query("SELECT * FROM users WHERE `username` = '".$username."' AND `password` = '".md5($password)."'");
        return $result->num_rows > 0;
    }

    function getUsers() {
        return $this->conn->query("SELECT * FROM users")->fetch_assoc();
    }

    function getTasksForUser($user_id) {
        return $this->conn->query("SELECT * FROM tasks WHERE assigned_user_id = ".$user_id."");
    }

    function getUserByUsername($username){
        return $this->conn->query("SELECT * FROM users WHERE `username` = '".$username."'")->fetch_assoc();
    }
}
?>