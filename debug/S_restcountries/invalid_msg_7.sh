curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=CW;KN;CD;TZ;PK;SD;BH;AZ;UA;GR;KP;VN;PY;GH;TN;GN;IN;GU;LC;MM;LA;QA;NL;CC;GL;SS;BF;JO;AW;LI;CO;DZ;IM;IL;GW;US;WS;ML;IT;MF;FO;CV;EE;BL;ZW;ZA;PT;FI;TR;SM;DM;BE;HR;GY;CY;BG;BV;JP;MP;BM;SC;DE;TO;BB;AR;TL;TH;MO;MK;ET;UM;VE;KW;GB;SE;HU;BD;AI;LB;PA' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=HA' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

