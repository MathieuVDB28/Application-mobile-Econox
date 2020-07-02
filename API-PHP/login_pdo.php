<?php
header("Content-Type: application/json; charset=iso-8859-1");
include 'connexionBDD.php';

if(isset($_GET['nom']) && isset($_GET['motdepasse']))
{
    if(!empty($_GET['nom']) && !empty($_GET['motdepasse']))
    {
        $nom = utf8_decode($_GET['nom']);
        $motdepasse = utf8_decode($_GET['motdepasse']);

        try
        {
          $statement = $bdd->prepare('SELECT * FROM agent WHERE Nom=:nom and Mdp=:motdepasse');
          $statement->execute(array(
                        'nom' => $nom,
                        'motdepasse' => $motdepasse));
          $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        }
        catch (Exception $e)
        {
            $data = array('Erreur'=> "Une exception est survenue");
            echo json_encode($data);
            die('Erreur :' . $e->getMessage());
        }
        if (!empty($results)) {
            echo json_encode($results[0]);
        }else {
            echo json_encode(array("Erreur"=>"Utilisateur inconnu"));
        }
    }
}
?>
