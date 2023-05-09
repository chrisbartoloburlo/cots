curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=SG;GB;GL;SA;BO;NU;TW;KR;PY;JO;TH;WF;CM;BB;SY;TZ;KI;KW;MS;CK;LT;MA;SC;BM;UG;EE;IS;PG;MH;ID;GY;SK;GF;TG;GM;TT;GD;HN;MM;BN;AU;DJ;CN;PW;BZ;BJ;DO;BS;CV;SE;AI;SN;HM;LB;KG;AQ;JP;DK;MF;JM;YT' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=IV' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

