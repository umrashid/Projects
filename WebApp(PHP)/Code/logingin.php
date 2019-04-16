<?php
if(isset($_POST['storeNumber']) && isset($_POST['password'])){
    $username = $_POST['storeNumber'];
    $password = $_POST['password'];
    $query = 'select passwordLogin from userinfo where username=:username';
        $statement = $db->prepare($query);
        $statement->bindValue(':username', $username);
        $statement->execute();
        $result = $statement->fetch();
        $statement->closeCursor();

    if(password_verify($password, $result[0])){
        $_SESSION["storeNumber"] = $username;
        $_SESSION['Logged_in'] = true;
        header("location: login.php");
    }else{
        $_SESSION['message'] = "Password Does Not Match";
        $_SESSION['storeNumber'] = $username;
    }
}

?>