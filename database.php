<?php
class Database {
    private $servername = "127.0.0.1";
    private $username = "root";
    private $password = "root";
    private $conn;

    function __construct() {
        $this->connect();
    }

    public function connect() {
        $this->conn = new mysqli($this->servername, $this->username, $this->password);
        if ($this->conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
    }

    public function close() {
        $this->conn->close(); 
    }

    public function query($query) {
        return $this->conn->query($query);
    }
}
?>