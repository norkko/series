echo Creating secret
kubectl create secret generic secrets --from-env-file=../.env

echo Adding mysql
kubectl apply -f ./mysql

echo Adding rest api
kubectl apply -f ./rest

echo Adding web
kubectl apply -f ./web

kubectl cluster-info
kubectl describe svc web
