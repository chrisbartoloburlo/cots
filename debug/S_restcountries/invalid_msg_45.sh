curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=VC;KN;SI;IM;GQ;GN;NE;MZ;HK;NR;CV;KG;BG;CM;ES;GU;NZ;AD;TF;LV;FJ;AR;AS;CI;UA' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=ON' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

