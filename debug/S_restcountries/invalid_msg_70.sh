curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=KZ;WF;ME;AU;SG;MD;SO;BM;PA;GD;CU;MM;SI;BJ' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=VR' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

