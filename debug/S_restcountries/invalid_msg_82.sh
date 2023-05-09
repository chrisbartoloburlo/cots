curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=SH;AE;PH;TW;AF;GI;VA;TH;SN;SZ;TJ;CN;MF;GT;LK;KR;SA;EE;PE;JE;MA;MY;NL;UZ;AZ;ID;AQ;NI;TZ;GS;TR;US;KN;GM;AS;BL;PL;NA;BS;MS;BB;MU;BI;UG;GP;PK;SG;BJ;VC;CZ;GE;MG;NE;VN;NP;MC;ER;PG;SC;PA;UA;CI;BD;DM;SD;SS' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=PI' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

