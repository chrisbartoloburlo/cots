curl \
  --request GET \
  --url 'http://localhost:8080/v3/user/login?username=user&password=user' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v3/pet' \
  --header 'Content-Type: application/json' \
  --data '{"id":123,"category":{},"name":"name","photoUrls":[""],"tags":[{"id":123,"name":"tag"}],"status":{}}' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v3/pet/123' \
  --header 'Content-Type: application/json' \
  --header 'api_key: special-key' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v3/pet/findByTags?tags=123' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v3/pet/findByStatus?status=pending' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request PUT \
  --url 'http://localhost:8080/v3/pet' \
  --header 'Content-Type: application/json' \
  --data '{"id":123,"category":{},"name":"name","photoUrls":[""],"tags":[{"id":123,"name":"tag"}],"status":{}}' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v3/pet/123' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --form '{"name":"updatedName","status":"pending"}' \
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
  --request GET \
  --url 'http://localhost:8080/v3/store/inventory' \
  --header 'Content-Type: application/json' \
  --header 'api_key: special-key' \
  --location \
  --max-redirs 32

