curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=KM' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=EA' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

