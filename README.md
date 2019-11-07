# 				(K-)AL2000 and 6beerVideo



# Software requirement specification



## Général

* 100 dvds peuvent être stockés au maximum dans la machine
* en cas d'un comportement non attendu -> afficher un message : voir gérant du  magasin 

## Client en général

* Enregistrement d'une trace de sa CB

* La location est débitée lors du rendu du film

* Si le client dépasse la durée de location maximum, il est débité du prix de la location + 10 euros

* Conservation d'un historique glissant sur 1 an

  

## !SUB

+ Une carte bleue ne peut avoir qu'un seule dvd en location
+ Lors de la location, récuperation nom/prénom et num CB
+ Prix de la location : 5euros/jour

## SUB

* Carte de fidélité

  * Liée à une CB

  * Une CB peut être liée à plusieurs cartes sub (infini)

  * La carte sub dispose d'un solde 

  * 15 euros de solde sont requis pour pouvoir effectuer une location

  * Pour réapprovisionner le solde, le montant est de 10euros minimum

    

* Lors de la création, le client reçois la carte par courrier dans les 7 jours

* Une carte sub peut avoir jusqu'a 3 dvds maximum en location

* La location d'un dvd est de 4euros/jours

* Toutes les 20 loc': 10 euro offerts et crédités sur le solde de la carte sub

* Controle parental :

  * Possibilité de limiter les catégories de films accessibles
  * Modifier le nombre de location maximum des cartes filles en respectant la contraintes de 3/cartes sub max.
  * Accés à l'historique des cartes filles
  * Possibilité de supprimer des cartes filles
    * si le solde de la carte fille n'est pas vide, crédit du solde sur  celui du master
    * si le solde est négatif, facturation sur la CB lié

* Une carte master peut se supprimer 

* Lors de la suppression de la carte master, ob supprime toutes ses cartes filles

## Stat

* Films les plus regardés/loués :
  *  du mois 
  *  du jour
  *  de la semaine

## Retour DVD

* Pour rendre il faut insérer CB/carte SUB
* si casse
  * Le client est en devoir de le déclarer
    * si déclaration : tarification de casse
    * sinon : tarification de casse doublée
* Lors du débit 
  * si sub et que le solde de la carte sub n'est pas suffisant elle passe 	en négatif, le client a alors 30j pour régulariser sa situation
  * sinon débit du montant
* Si erreur (exemple : dvd déjà rendu) : afficher un message : voir gérant du magasin 

## Ré-appro

* 1 fois par mois, intervention d'un technicien

* maj auto de la machine lors de la réappro : exigence a préciser

  

## Interfaces

* interface admin:

  * optionel : consulter historique de chaque client (même les !sub)

  * Gestion des recommandations 

  * Accés aux satistiques

    * durée location moyenne
    * coût moyen de lococation
    * les statistiques des films

  * Accés aux mêmes listes que les utilisateurs

  * Possibilité de retirer un dvd/ajouter un dvd

  * Modifier la durée maximum de location des dvd

    

* Interface loggin (à définir)

* interface standard

  * Liste de tous les films avec affichage de la disponibilité dans KAL2000

  * pour chaque film il faut afficher :

    * synopsis

    * acteurs

    * réalisateur

    * catégories

      

  * Liste des films dispo dans 6beerVideo

  * Affichage des recommandations (PUB)

    

* interface SUB (qui est une surcharge de l'interface standard):

  * Pour chaque dvd dans la liste des films de 6beerVideo ajout d'un bouton pour suggérer l'ajout d'un film
  * Affichage du nom associé à la carte
  * Affichage du solde de la carte
  * Affichage d'un bouton historique
  * Si la carte est Master :
    * ajout d'un bouton pour créer une carte fille
    * peut consulter l'historique des cartes filles
    * peut supprimer des cartes filles
