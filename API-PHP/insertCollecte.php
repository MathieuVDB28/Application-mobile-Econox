<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "econoxBDD";

if ($_SERVER['REQUEST_METHOD']=='POST' ){
  $id = $_POST['id'];
  $volume = $_POST['volume'];
  $tel = $_POST['tel'];
  $batterie = $_POST['batterie'];
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// verification  connection
if ($conn->connect_error) {
    die("Erreur de Connexion : " . $conn->connect_error);
}
$sql = "INSERT INTO donnees_collecte (id,volume,telephone,batterie)
VALUES ('$id','$volume','$tel','$batterie')";

if ($conn->query($sql) === TRUE) {
    echo "Ajout avec succes ";
} else {
    echo "Erreur d ajout : " . $sql . "<br>" . $conn->error;
}

$conn->close();
}
else
echo "Nom invalide" ;
?>
