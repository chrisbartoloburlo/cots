curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/callingcode/235' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/callingcode/0.7014683444951121' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=CL;NI;AQ;BO;MH;AW;SO;PG;VI;DK;NF;UZ;SL;TW;PL;DE;NO;GQ;RO;DO;GF;CW;PR;JE;BL;AO;AE;ID;KR;SM;BW;NZ' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=UI' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

