curl \
  --request GET \
  --url 'http://localhost:8080/v3/user/login?username=user&password=user' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v3/store/order' \
  --header 'Content-Type: application/json' \
  --data '{"id":456,"petId":456,"quantity":2}' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v3/store/order/456' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request DELETE \
  --url 'http://localhost:8080/v3/store/order/456' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v3/store/inventory' \
  --header 'Content-Type: application/json' \
  --header 'api_key: special-key' \
  --location \
  --max-redirs 32

