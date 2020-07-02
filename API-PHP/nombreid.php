<?php
include 'connexionBDD.php';
$req = $bdd->query('SELECT count(*) as Prenom FROM agent');
$result = $req->fetchAll(PDO::FETCH_ASSOC);
echo json_encode ($result[0]);
?>
