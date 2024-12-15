# Auto-évaluation ShowTime

Barrouillet Margaux  
Chevillotte Thomas  
Garcia Anthony  
Soutric Tanguy  
  
  ING2 GSI Groupe 1

## Présentation

#### Discover. Watch. Remember. It's ShowTime !

Cette application permet aux utilisateurs de découvrir et d'explorer un large éventail de films et de séries TV. 
Grâce à une interface simple et intuitive, l'utilisateur peut consulter les détails de chaque média, ajouter ses films et séries préférés à des listes personnalisées, et garder une trace des contenus qu'il souhaite regarder. 
De plus, il peut attribuer une note et un commentaire à chaque film ou série afin de partager son avis. 
Que ce soit pour enregistrer ses favoris, organiser ses futures découvertes ou donner son avis, l'application aide à gérer ses choix de divertissement facilement, tout en offrant une expérience de navigation agréable et fluide.

## Partie technique

- Nous avons développé notre application ShowTime en utilisant **Spring Boot**, avec **Spring Data JPA**, **H2 Database** et **Thymeleaf**.

- Nous avons utlisé l'architecture **MVC** pour séparer le traitement des données et leur affichage.

- Notre modèle comporte au moins **5 entités** différentes (6), avec des relations :
  - **Media** (séparée en 2 sous entités **Movie** et **TVShow**), modélisant les films et les séries affichées sur notre site
  - **MediaList**, modélisant toutes les listes présentes sur le site (détaillé dans les parties suivantes) 
  - **Actor**, représentant les Acteurs associés aux films et aux séries.
  - **Rating**, représentant les notes et les commentaires des utilisateurs pour les films et les séries.
  - **AverageRating**, représentant la note moyenne d'un film ou d'une série. 
  - **User**, représentant les comptes utilisateurs.
  
- Ces entités sont liées avec plusieurs relations : 
  - **One To One** : 
    - AverageRating avec Media, une note moyenne pour un Media. 
  - **One To Many** et **Many To One** :
    - Media avec Rating, plusieurs Ratings pour un Media.
    - MediaList avec User, un Utilisateur a plusieurs MediaList.
    - Rating avec User, un Utilisateur peut mettre plusieurs Ratings.
  - **Many To Many** : 
    - Actor avec MediaList, pour afficher les Medias dans lequel l'Acteur à joué, et inversement pour afficher tous les Acteurs d'un Media.
    - Media avec MediaList, pour créer des Listes de Medias.
    
- L'interface du site permet d'associer et de dissocier graphiquement ces entités, avec une page pour les informations des Medias (Film et Série), une page pour les informations des Actors, une page pour le profil Utilisateur, et des listes et des ratings intégrés dans les pages du site.
  
- Nous avons aussi implémenté des fonctionnalités CRUD en suivant notre **logique métier** spécifique : 
  - Création & Suppression de profil utilisateur 
  - Ajout & Suppresion de Rating
  - Ajout et Suppresion de Media à des listes 
  - Modification des informations Utilisateur

---

## Grille d'évaluation

### Fonctionnalités : **5 points**

- L'application contient bien les fonctionnalités demandées :  *5 entités avec diverses relations (1-1, 1-N et N-1, N-N), associations graphiques, logique métier de l'application.*
  
- L'application permet d'insérer, mettre à jour, supprimer, chercher une entité en BDD : 
  - Insertion : *Création d'Utilisateur et création de Ratings*
  - Mise à jour : *Modification des informations de connexion* 
  - Suppression : *Suppression d'un utilisateur, d'un Rating*
  - Recherche : *Barre de recherche de Media et d'Actor.*
  
- L'application permet de lier deux entités en BDD / permet, pour une entité donnée, de créer un lien à une autre entité en BDD : *Ajout de Rating, lié à un User et à un Media, ajout d'un Media à une Liste, lié à un User.*

### Technique : **5 points**

- L'application utilise le design pattern MVC pour chaque fonctionnalité : *Séparation de l'implémentation en 3 couches, Model, Vue et Controller.*
  
- Les contrôleurs utilisent les méthodes HTTP : GET, POST, PUT, DELETE : *Méthode GET pour afficher les pages et POST pour ajouter, supprimer et modifier des informations (préférées à PUT et DELETE pour éviter les erreurs et pouvoir vérifier avant d'effectuer une action).*
  
- Chaque vue manipule des données transmises par son contrôleur : *les vues affichent uniquement les données transmises par les contrôleurs (listes de Media, détails d'Acteurs/de Media, informations de profil, résultats de recherche, etc.*

### Qualité : **4/5 points**

- L'application est jolie / utilise un framework CSS : *Tailwind CSS a été utilisé pour créer une interface utilisateur minimaliste et simple à utiliser pour garantir la meilleur expérience utilisateur possible.*
  
- Le code source est livré dans un repo GitHub/GitLab. Il est de bonne qualité : *Le projet est hébergé sur GitHub avec un code organisé (branches, packages, dossiers) et des commentaires explicatifs.*
  
- Le repo comporte des commits réguliers de chaque membre du groupe : *Nous avons assuré des commits réguliers tout au long du projet. La répartition des contributions a varié selon les tâches, mais chaque membre a participé au développement, garantissant une progression cohérente et collective.* 

---

## Conclusion

### Points forts
- Toutes les fonctionnalités demandées ont été intégrées avec succès.  
- L'application respecte l'architecture MVC et utilise pleinement la BDD.  
- Chaque entité est visuellement dissociable des autres et facile à utiliser.
- Nous avons utilisé l'[API TMDB](https://developer.themoviedb.org/reference/intro/getting-started) pour remplir notre base de données de films, de séries et d'acteurs.

### Points à améliorer
- Une répartition plus équilibrée des tâches au sein de l'équipe aurait permis d'homogénéiser les contributions de chacun.

### Auto-évaluation finale : **14/15**
