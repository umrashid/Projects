<?php 
session_start();
if(isset($_SESSION['Logged_in'])){

}else{
  header("location: index.php");
}
require_once('databaseconnection.php');
if(isset($_GET["productID"])){
    $data = (int)(filter_input(INPUT_GET, 'productID',
    FILTER_VALIDATE_INT));
          $query = 'select i.storeID, p.productId, p.Description, i.Quantity, p.Price, p.Weight
           from inventory i, product p, stores s
           where i.productID = p.productID
           and i.storeID = s.storeID
           AND i.productID = :productID
           and i.storeID = :storeNumber';
           $statement = $db->prepare($query);
           $statement->bindValue(':productID', $data);
           $statement->bindValue(':storeNumber', $_SESSION['storeNumber']);
           $statement->execute();
           $table = $statement->fetch();
           $statement->closeCursor();
}
if(isset($_GET["updatedQuantity"]) && isset($_GET["updatedProductID"]) && isset($_GET["updatedStoreID"])){
    $updatedQuanity = (int)(filter_input(INPUT_GET, 'updatedQuantity',
    FILTER_VALIDATE_INT));
    echo $updatedQuanity;
    $updatedProductID = (int)(filter_input(INPUT_GET, 'updatedProductID',
    FILTER_VALIDATE_INT));
    $updatedStoreID = (int)(filter_input(INPUT_GET, 'updatedStoreID',
    FILTER_VALIDATE_INT));
          $query = 'Update inventory
          Set quantity = :updatedQuantity
          where productID = :updatedProductID
          and storeID= :updatedStoreID';
           $statement = $db->prepare($query);
           $statement->bindValue(':updatedQuantity', $updatedQuanity);
           $statement->bindValue(':updatedProductID', $updatedProductID);
           $statement->bindValue(':updatedStoreID', $updatedStoreID);
           $statement->execute();
           //$table = $statement->fetch();
           $statement->closeCursor();
           $updateSuccess = true;
}
?>

<!DOCTYPE html>
<html>
<title>Inventory Management</title>

<!-- Including all the links -->
<?php include 'links.php'; ?>

<link href="search.css" rel="stylesheet" />
<link href="update.css" rel="stylesheet" />

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

<body>
<!-- Including the Navbar -->
<?php include 'navbar.php'; ?>

<!-- Search Options -->
<div id="firstForm" class="styling">
    <form action="update.php" method="GET">
    <div class="form-group">
            <select class="custom-select" disabled>
                <option selected>Store Number: <?php echo $_SESSION['storeNumber']?></option>
            </select>
    </div>
    <div class="form-group">
        <input type="number" class="form-control" name="productID" placeholder="Product Number" min="1" max="100" required>
    </div>
    <button  type="submit" class="btn btn-success w-100 buttoncolor">Submit</button>
    </form>
</div>
<div id="secondForm" class="styling" style="display:none">
    <form action="update.php" method="GET">
    <div class="form-group">
            <select class="custom-select" name="" disabled>
                <option selected>Store Number: <?php echo $_SESSION['storeNumber']?></option>
            </select>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="" value="<?php echo "Product ID: " .$table[1] ?>"  disabled> 
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="" value="<?php echo "Product Description: " .$table[2] ?>"  disabled> 
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="" value="<?php echo "Product Price: " ."$".$table[4] ?>"  disabled> 
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="" value="<?php echo "Product Weight: " .$table[5]. " g" ?>"  disabled> 
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="" value="<?php echo "Current Product Quantity: " .$table[3] ?>"  disabled> 
    </div>
    <div class="form-group">
        <input type="number" class="form-control" name="updatedQuantity" placeholder="Updated Quanity" min="1" max="20" required>
    </div>
    <div class="form-group">
        <input type="hidden" class="form-control" name="updatedProductID" value="<?php echo $table[1]?>">
    </div>
    <div class="form-group">
        <input type="hidden" class="form-control" name="updatedStoreID"  value =<?php echo $table[0]?>>
    </div>
    <button  type="submit" class="btn btn-success w-100 buttoncolor">Update</button>
    </div>
    
    </form>
</div>
<!-- Modal -->
<div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Update Successful</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Your Updated the inventory for <?php echo $_SESSION['storeNumber'] ?> on <?php echo date("Y/m/d") ?>.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
                </div>
            </div>
            </div>



    
    

 <!--Navbar -->
<script src="js/navbar.js"></script>
<!-- choices.js -->
<script src="js/extention/choices.js"></script>
<!--Jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
 <?php 
if(isset($_GET["productID"])){
    echo '$("#firstForm").fadeOut();';
    echo 'setTimeout(function(){ $("#secondForm").fadeIn(); }, 1000);';
}

?>       
</script>
<?php
    if(isset($updateSuccess) && $updateSuccess == true){
        echo "<script> $('document').ready(function() { $('#confirmationModal').modal('show'); }); </script>";
        $updateSuccess = false;
    }

?>

</body>
</html>
