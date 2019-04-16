<?php
    $dsn = 'mysql:charset=utf8mb4;host=localhost;dbname=inventory_database';
    $username = 'user';
    $password = 'password';

    try {
        $db = new PDO($dsn, $username, $password);
    } catch (PDOException $e) {
        $error_message = $e->getMessage();
        include('database_error.php');
        exit();
    }
?>