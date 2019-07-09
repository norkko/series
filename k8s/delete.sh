kubectl delete -f mysql/
kubectl delete -f rest/
kubectl delete -f web/
kubectl delete secret secrets
echo Deleted deployment