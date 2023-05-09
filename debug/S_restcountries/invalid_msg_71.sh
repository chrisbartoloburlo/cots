curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=CZ;KP;LR;CF;PT;CU;YT;VC;TM;GU;GT;SN;SZ;NG;TT;BJ;CM;FJ;RW;FO;SC;RU;YE;AT;MD;ME' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=YH' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

