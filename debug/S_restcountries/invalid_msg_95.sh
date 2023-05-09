curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=MZ;DM;ST;NP;YE;TK;BY;LT;BB;CH;JO;BA;AX;WF;MQ;LV;CN;KH;TC;TN;ML;TW;GL;BO;VC;HK;AT;MY;TH;QA;RO;FK;AI;SC;LY;GI;UM;BW;BF;BJ;IR;MC;CL;BE;KG;GQ;GG;AE;NA;TL;BH;CA;MH;PE;BQ;LK;GH;FM;PR;NL;HU;GD;MV;MG;MO;UA;RW;FO;GT;RU;FI;IE;BV;GU;KR;SN;CZ;AW;AF;NO;EC' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=DR' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

