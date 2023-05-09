curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=MY;CD;DO;ZM;PH;SM;EC;MM;TC;MV;CF;NE;DE;MS;BJ;WF;PE;BV;XK;PK;GS;ID;SN;NO;MC;CU;BR;SV;SZ;LR;YE;MP;MA;MZ;IE;GQ;KN;US;CX;DK;NR;GA;SK;AZ;CM;GT;RO;GG;OM;GM;KY;TO;PS;AQ;RW;CL;IL;FJ;GU;IR;GL;NI;MD;ER' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v1/alpha?codes=DG' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

