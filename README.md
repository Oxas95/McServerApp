# McServerApp
Permet de lancer un fichier .bat destiné à lancer un serveur minecraft dont le protocole RCON est activé.  
En lien avec : https://glitch.com/~mcserverapp

## Usage
*McServerApp.jar* réceptionne les messages pour lancer ou arrêter un server minecraft : ```java -jar McServerApp.jar <fichier de configuration>```  
*client.jar* permet de tester l'envoi de message : ```java -jar client.jar <Ip> <Port> <"message">```

# Librairies requises pour build à ajouter dans le *build path*
https://mvnrepository.com/artifact/org.json/json/20140107  
https://github.com/Kronos666/rkon-core
