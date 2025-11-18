# Script de déploiement Kubernetes - Étape 5
# YourWayToItaly

Write-Host "=== Déploiement sur Kubernetes ===" -ForegroundColor Cyan

# Appliquer les fichiers YAML
Write-Host "`n1. Déploiement des applications..." -ForegroundColor Yellow
kubectl apply -f deployment.yaml

Write-Host "`n2. Création des services..." -ForegroundColor Yellow
kubectl apply -f service.yaml

Write-Host "`n3. Configuration de l'Ingress..." -ForegroundColor Yellow
kubectl apply -f ingress.yaml

Write-Host "`n4. Attente du démarrage des pods..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

Write-Host "`n5. Initialisation de la base de données..." -ForegroundColor Yellow
kubectl delete job init-db 2>$null
kubectl apply -f init-db-job.yaml
kubectl wait --for=condition=complete job/init-db --timeout=60s

Write-Host "`n6. État du cluster:" -ForegroundColor Green
kubectl get all

Write-Host "`n7. Ingress:" -ForegroundColor Green
kubectl get ingress

Write-Host "`n=== Déploiement terminé ===" -ForegroundColor Cyan
Write-Host "`nPour accéder à l'application:" -ForegroundColor Green
Write-Host "1. Ouvrez un terminal et exécutez: minikube service ywti-app --url" -ForegroundColor Yellow
Write-Host "2. Gardez ce terminal ouvert" -ForegroundColor Yellow
Write-Host "3. Accédez à l'URL affichée + /html/index.html dans votre navigateur" -ForegroundColor Yellow
