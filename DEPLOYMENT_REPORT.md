# Rapport de Déploiement - YourWayToItaly sur Kubernetes

## Étape 4 : Docker - Containerisation

### Objectif
Containeriser l'application JEE YourWayToItaly pour faciliter son déploiement.

### Actions Réalisées

1. **Création du Dockerfile multi-stage**
   - **Stage 1 (Builder)** : Utilisation de `maven:3.9-eclipse-temurin-17` pour compiler le projet et générer le fichier WAR
   - **Stage 2 (Runtime)** : Utilisation de `tomcat:9-jdk17` pour déployer l'application
   - Script de démarrage personnalisé pour remplacer les variables d'environnement dans `context.xml` au runtime

2. **Build et publication de l'image Docker**
   ```bash
   docker build -t gyro0/yourwaytoitaly:1.0 .
   docker push gyro0/yourwaytoitaly:1.0
   ```
   - Image publiée sur Docker Hub : `gyro0/yourwaytoitaly:1.1` (version finale avec correction CORS)

3. **Test local de l'image**
   ```bash
   docker run -p 8080:8080 -e POSTGRES_HOST=host.docker.internal gyro0/yourwaytoitaly:1.0
   ```

---

## Étape 5 : Kubernetes - Déploiement

### Objectif
Déployer l'application containerisée sur un cluster Kubernetes (Minikube) avec une base de données PostgreSQL.

### Architecture Déployée

- **PostgreSQL** : 1 replica, port 5433, PVC de 1Gi pour la persistance
- **Application YourWayToItaly** : 2 replicas pour la haute disponibilité
- **Service NodePort** : Exposition de l'application sur le port 30080

### Fichiers Kubernetes Créés

1. **`deployment.yaml`** : Définit les déploiements PostgreSQL et de l'application
2. **`service.yaml`** : Services ClusterIP pour PostgreSQL et NodePort pour l'application
3. **`ingress.yaml`** : Configuration Ingress (non fonctionnel, NodePort utilisé à la place)
4. **`init-db-job.yaml`** : Job Kubernetes pour l'initialisation automatique de la base de données

### Commandes de Déploiement

```bash
# Déploiement complet automatisé
.\k8s\deploy.ps1

# Accès à l'application
minikube service ywti-app --url
```

### État du Cluster

```bash
kubectl get all
```

**Résultat** :
- **3 pods déployés** : 1 PostgreSQL + 2 instances de l'application
- Tous les pods en état `Running`
- Service NodePort actif sur le port 30080

---

## Problèmes Rencontrés et Solutions

### 1. PostgreSQL CrashLoopBackOff
**Problème** : Le pod PostgreSQL ne démarrait pas avec l'erreur "root execution not permitted"

**Solution** : Ajout d'un `securityContext` dans le déploiement
```yaml
securityContext:
  runAsUser: 999
  fsGroup: 999
  runAsNonRoot: true
```

### 2. Erreurs CORS
**Problème** : L'application JavaScript ne pouvait pas communiquer avec le backend car `utils.js` utilisait une URL codée en dur (`http://localhost:8080`)

**Solution** : Modification de `utils.js` pour utiliser l'origine dynamique
```javascript
const contextPath = window.location.origin;
```
- Reconstruction de l'image Docker (version 1.1)
- Chargement dans Minikube avec `minikube image load gyro0/yourwaytoitaly:1.1`

### 3. Base de Données Non Initialisée
**Problème** : Erreur HTTP 500 car les tables n'existaient pas dans PostgreSQL

**Solution** : Création d'un Job Kubernetes (`init-db-job.yaml`) qui :
- Crée automatiquement les 8 tables nécessaires avec `CREATE TABLE IF NOT EXISTS`
- Insère les données initiales (110 villes, 15 types d'annonces, données échantillon)
- Vérifie si les tables sont vides avant d'insérer pour éviter les doublons
- S'exécute automatiquement via le script `deploy.ps1`

### 4. Ingress Non Fonctionnel
**Problème** : L'accès via `http://ywti.local` ne fonctionnait pas malgré la configuration Ingress

**Solution** : Utilisation du service NodePort avec tunnel Minikube
```bash
minikube service ywti-app --url
```

---

## Résultats Finaux

### URL d'Accès
L'application est accessible via le tunnel Minikube :
```
http://127.0.0.1:<PORT>/html/index.html
```
*(Le port est attribué dynamiquement par le tunnel)*

### Vérifications Effectuées

1. **Tables créées** : 8 tables (City, Type_advertisement, Company, Tourist, Advertisement, Tourist_Advertisement, Review, Image)
2. **Données insérées** : 110 villes, 15 types d'annonces
3. **Application fonctionnelle** : Interface accessible, pas d'erreurs CORS, connexion à la base de données réussie

### Commande kubectl get all

```
NAME                                      READY   STATUS    RESTARTS   AGE
pod/postgres-deployment-567cbc9f8-xxxxx   1/1     Running   0          10m
pod/ywti-app-xxxxxxxxx-xxxxx              1/1     Running   0          10m
pod/ywti-app-xxxxxxxxx-xxxxx              1/1     Running   0          10m

NAME                 TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE
service/postgres     ClusterIP   10.xxx.xxx.xxx   <none>        5433/TCP       10m
service/ywti-app     NodePort    10.xxx.xxx.xxx   <none>        80:30080/TCP   10m

NAME                                  READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/postgres-deployment   1/1     1            1           10m
deployment.apps/ywti-app              2/2     2            2           10m

NAME                                            DESIRED   CURRENT   READY   AGE
replicaset.apps/postgres-deployment-567cbc9f8   1         1         1       10m
replicaset.apps/ywti-app-xxxxxxxxx              2         2         2       10m
```

---

## Automatisation

Le script `deploy.ps1` automatise l'ensemble du processus de déploiement :
1. Application des manifestes Kubernetes (deployment, service, ingress)
2. Attente du démarrage des pods (30 secondes)
3. **Initialisation automatique de la base de données** via le Job `init-db`
4. Affichage de l'état du cluster
5. Instructions pour accéder à l'application

**Commande unique pour tout déployer** :
```powershell
.\k8s\deploy.ps1
```

---

## Technologies Utilisées

- **Containerisation** : Docker (multi-stage build)
- **Orchestration** : Kubernetes (Minikube)
- **Base de données** : PostgreSQL 15
- **Serveur d'applications** : Apache Tomcat 9
- **Langage** : Java 17, JEE
- **Build** : Maven 3.9

---

## Conclusion

Le déploiement de l'application YourWayToItaly sur Kubernetes a été réalisé avec succès. L'application est entièrement fonctionnelle avec 2 instances pour la haute disponibilité, une base de données PostgreSQL persistante, et un processus d'initialisation automatisé. Les problèmes de configuration (CORS, permissions PostgreSQL, initialisation DB) ont été résolus de manière robuste et reproductible.
