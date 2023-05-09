curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/region/Asia' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=VU;AU;HU;UY;MD;CO;SH;RS' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=QQ' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

