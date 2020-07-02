<?php

try
{
  $user = 'root';
  $pass = '';
  $bdd  = new PDO('mysql:host=localhost;dbname=econox;charset=utf8',$user,$pass, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

}

catch(Exception $e)
{
  die('Erreur : '.$e->getMessage());
}
