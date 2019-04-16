<?php 

require_once('databaseconnection.php');
session_start();
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    if(isset($_POST['logingin'])){
        require 'logingin.php';
    }
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

</head>
<body>
    
        <div class="container-fluid page">
            <div class="row d-flex justify-content-center h-100">
           <!-- <div class="col-3"></div> -->
                <div class="shadow p-5 bg-white h-50 align-self-center">
                    <h1 class="heading">Inventory Management</h1>
                    <form action="index.php" method="POST">
                        <div class="form-group">
                            <label for="username">Store Number</label>
                            <input type="number" class="form-control" name="storeNumber"  aria-describedby="emailHelp" placeholder="Enter Store Number" min='6601' max='6610' required>
                            <small  class="form-text text-muted">Example: 3390.</small>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <input type="password" class="form-control"  name="password" placeholder="Enter Password Here" required>
                        </div>
                        <br>
                        <button type="submit" class="btn btn-success w-100" name="logingin">Submit</button>
                        <br>
                        <?php 
                            if(isset($_SESSION['message'])){
                                echo '<p style="color:red;">'.$_SESSION['message']. " for Store Number : ". $_SESSION['storeNumber'] ."</p>";
                                session_destroy();
                            }
                            
                        
                        ?>
                    </form>

                </div>
            </div>
            
            
        </div>
        
    

</body>
</html>