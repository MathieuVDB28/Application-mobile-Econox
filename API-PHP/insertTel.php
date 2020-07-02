<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "econox";

if ($_POST["tel"]<>"" ){
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// verification  connection
if ($conn->connect_error) {
    die("Erreur de Connexion : " . $conn->connect_error);
}
$tel=$_POST["tel"];
$sql = "INSERT INTO container (RFID, Taille, Volume, PoidsDeBase, Localisation, telephone, Batterie_idBatterie, Site_idSite)
VALUES ('null','null','null','null','null','$tel','1','1')";

if ($conn->query($sql) === TRUE) {
    echo "Ajout avec succes ";
} else {
    echo "Erreur d ajout : " . $sql . "<br>" . $conn->error;
}

$conn->close();
}
else
echo "Téléphone invalide" ;
?>
