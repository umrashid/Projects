<?php 
require_once("databaseconnection.php");
session_start();
if(isset($_SESSION['Logged_in'])){

}else{
  header("location: index.php");
}

if(isset($_POST['newPassword1']) && isset($_POST['newPassword2']) && isset($_POST['currentPassword'])){
        
        $currentPassword = $_POST['currentPassword'];
        $newPassword1 = $_POST['newPassword1'];
        $newPassword2 = $_POST['newPassword2'];
        $actualPassword = getPassword($db);

    if(!password_verify($currentPassword,$actualPassword)){
        $_SESSION['error'] = "Invalid Current Password";
    }    
    else if($newPassword1 == $newPassword2){
        $newPassword1 = password_hash($newPassword1, PASSWORD_DEFAULT);    
        $query = 'UPDATE userinfo 
                SET passwordLogin = :newPassword1 
                where username= :username ';
            $statement = $db->prepare($query);
            $statement->bindValue(':username', $_SESSION['storeNumber']);
            $statement->bindValue(':newPassword1', $newPassword1);
            $statement->execute();
            $statement->closeCursor();
            $changePasswordSuccess = true;
            $_SESSION['error'] = "";
    
    }elseif(isset($_POST['confirmPassword'])){
        $_SESSION['error'] = "Mismatched Passwords!";
    }

}
function getPassword($db){
    $query = 'select passwordLogin from userinfo where username=:username';
    $statement = $db->prepare($query);
    $statement->bindValue(':username', $_SESSION['storeNumber']);
    $statement->execute();
    $result = $statement->fetch();
    $statement->closeCursor();
    return $result[0];
}
?>



<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Inventory Management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="style.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    
<!-- Including all the links -->
<?php include 'links.php'; ?>

<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", sans-serif}
body, html {
    height: 100%;
    line-height: 1.8;
}

.w3-bar .w3-button {
    padding: 16px;
}
</style>
</head>
<body>
<!-- Including the Navbar -->
<?php include 'navbar.php'; ?>

        <div class="container-fluid page">
            <div class="row d-flex justify-content-center h-100">
           <!-- <div class="col-3"></div> -->
                <div class="shadow p-5 bg-white h-50 align-self-center">
                    <h1 class="heading">Change Password for Store <?php echo $_SESSION['storeNumber']?></h1>
                    <br>
                    <form action="changePassword.php" method="POST">
                        <div class="form-group">
                            <input type="text" class="form-control" name="storeNumber"  aria-describedby="emailHelp" value="<?php echo "Store Number : ".$_SESSION['storeNumber']?>" min='6601' max='6610' disabled>
                    
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control"  name="currentPassword" placeholder="Enter your Current Password Here" required>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control"  name="newPassword1" placeholder="Enter your New Password Here" required>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control"  name="newPassword2" placeholder="Enter your New Password Again" required>
                        </div>
                        <br>
                        <button type="submit" class="btn btn-success w-100" name="confirmPassword" >Confirm Changes</button>
                        <p style="color:red">
                        <?php 
                        if(isset($_SESSION['error'])){
                            echo $_SESSION['error'];
                        }
                        ?>
                        </p>
                        <br>
                        
                    </form>

                    
                </div>

            </div>
            
            
        </div>
        <!-- Modal -->
            <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Passwowd is successfully changed</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Your Password is succesfully changed on <?php echo date("Y/m/d") ?>. From now on you will be using your new password to sign in.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
                </div>
            </div>
            </div>
    
<!--Navbar -->
<script src="js/navbar.js"></script>
<!--Jquery -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<?php
    if(isset($changePasswordSuccess) && $changePasswordSuccess == true){
        echo "<script> $('document').ready(function() { $('#confirmationModal').modal('show'); }); </script>";
        $changePasswordSuccess = false;
    }

?>

</body>
</html>