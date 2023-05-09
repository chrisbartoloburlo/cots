curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=TG;NC;MN;MD;PE;WF;HK' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=ZD' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

