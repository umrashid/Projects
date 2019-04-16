<!DOCTYPE html>

<?php 
session_start();
if(isset($_SESSION['Logged_in'])){

}else{
  header("location: index.php");
}
require_once('databaseconnection.php');
$data = $_GET["data"];
$query = "select i.storeID,p.Description, i.Quantity, s.City, s.Phone_Number
from inventory i, product p, stores s
where i.productID = p.productID
      and i.storeID = s.storeID
        AND p.description Like '$data%'";
        $statement = $db->prepare($query);
        //$statement->bindValue(':productName', $data);
        $statement->execute();
        $table = $statement->fetchAll();
        $statement->closeCursor();

?>



<html>
<title>Inventory Management</title>

<!-- Including all the links -->
<?php include 'links.php'; ?>



<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", sans-serif}
body, html {
    height: 100%;
    line-height: 1.8;
    background-color: #a598ee;
}

.w3-bar .w3-button {
    padding: 16px;
}
</style>
<body>
<!-- Including the Navbar -->
<?php include 'navbar.php'; ?>

<div class="container bg-light p-5 mt-5">
    <div id ="showTable">
        <table id="viewTable" class="stripe">
                    <thead>
                        <tr>
                        <th scope="col">Store Number</th>
                        <th scope="col">Description</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">City</th>
                        <th scope="col">Phone Number</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php foreach($table as $tables) : ?> 
                            <tr>
                                <td> <?php echo $tables['storeID']; ?> </td>
                                <td> <?php echo $tables['Description']; ?> </td>
                                <td> <?php echo $tables['Quantity']; ?> </td>
                                <td> <?php echo $tables['City']; ?> </td>
                                <td> <?php echo $tables['Phone_Number']; ?> </td>
                            </tr>
                        <?php endforeach ?>
                    </tbody>
        </table>
    </div>
</div>


 <!--Jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <!--Navbar -->
 <script src="js/navbar.js"></script>
<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script> 
$(document).ready( function () {
    $('#viewTable').DataTable();
});
</script>

</body>
</html>
