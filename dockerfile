# Utiliser Tomcat avec JDK 17 comme image de base
FROM tomcat:10.1-jdk17

# Supprimer l'application web par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copier le fichier WAR produit par Jenkins dans Tomcat
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

# Exposer le port de Tomcat
EXPOSE 8080

# Démarrer Tomcat
CMD ["catalina.sh", "run"]