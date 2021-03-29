## Eiffel Cars makes your car purchase easier ![eiffel_notif](https://user-images.githubusercontent.com/64874813/112875501-8f791900-90c4-11eb-8278-c9a13f5ef479.png)

**Version 1.0.0** 

La société Eiffel Cars offre à ses utilisateurs la possibilité d'acheter les voitures en meilleures prix.

L’application permet aux utilisateurs d’acheter des voitures en toute sécurité et avec beaucoup de fonctionnalités. Premièrement, l’utilisateur peut s’authentifier soit avec son email et mot de passe, soit avec l’empreinte pour faciliter l’accès à l’application. Après l’authentification, l’utilisateur peut découvrir toutes les voitures disponibles en Euros, mais l’application permet à l’utilisateur de changer à la devise préférer dans l’interface des voitures et aussi chercher la voiture souhaitée par mot clé ou autre chose. Ensuite il peut ajouter la voiture au panier pour l’acheter. Dans le Checkout, l’utilisateur ajoute les informations nécessaires pour effectuer l’achat et aussi prendre une photo de la carte ID, Finalement, l’utilisateur reçoit une notification sur le téléphone et une facture par mail, qui contient le nom de voiture et le prix total.

## Technologies:

Cette application Android est réalisé avec SDK 29, et la version minimale supporté SDK 23.

Les technologies que nous avons utilisé :
- BiometricManage : pour l'authentification avec empreinte.
- Picasso : pour l'affichage des images à partir d'un URL.
- SmartiesImageSlider : pour affichages des images dans un slider view.
- SQLite Database : utilisé dans la première version d'authentification.
- RecyclerView: pour l'affichage dans une liste.
- HttpURLConnection : pour communiquer avec le service web.
- NotificationManager : pour recevoir les notifications.
- SharedPrefereneces : pour mémoriser la session d'authentification
- Google API : pour une authentification secondaires.

Pour ce projet, nous avons utilsé une architecture client-serveur, notre serveur est héberger sur le serveur de heroku : https://carsho.herokuapp.com/ :
- La base de données utilisé est MySQL AWS, pour que tous les membres de l'équipe travaillent sans problème.
- Service web est codé avec Java 1.8, nous avons utilisé framework Spring, Hibernate . Lien GitHub : https://github.com/JANATI-IDRISSI-Imad/ServicesWebCarShop
- Api utilisé pour convertir la monnaie est : https://apilayer.com/


## Informations:

Pour la partie de paiement avec la carte bancaire, pour le moment c'est juste une simulation de paiement, on ne prend pas d'argent à partir de votre carte bancaire :)

## Contributeurs: 
- [Ghita MAKHLAS](https://github.com/ghitaMakhlas) gmakhlas@etud.u-pem.fr
- [Driss NFIFI](https://github.com/Driss-Nfifi) dnfifi@etud.u-pem.fr
- [Imad JANATI-IDRISSI](https://github.com/JANATI-IDRISSI-Imad) ijanatii@etud.u-pem.fr
- [Khalil BAKRI](https://github.com/LiiLkhaa) kbakri@etud.u-pem.fr
- [Saad ZRIDI](https://github.com/Zsaad7) szridi@etud.u-pem.fr

## License & copyright: 
:copyright: 2021
[Apache-2.0 License](LICENSE)
