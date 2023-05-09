curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=SZ;PA;PR;CD;TO;TD;IR;FR;NP;WS;ML;US;AM;CC;HM;AS;TR;SA;VN;CK;HT;SY;KE;JM;LR;BI;BT;TC;VA;CA;TK;NZ;DE;UM;SV;NI;PE;SR;NA;CI;ID;CN;QA;RE;SX;AI;TM;AD;NU;DK;MT;TN;HU;GL;FI;HK;CW;BY;BB;DM;BH;LU;LK;BW;SO;AU;IE;TT;KN;CH;PF;TG;AW;BV;CY;UY;IS;AR;AF;MS;PT;BJ;CO;MF;CG;SK;MK;KG;LT;NR;SN;MC;TH;SM;MV;BE;KP;KH;AX;TW;FM;AL;CX;MP;BD;LS;BG;UZ;LB;YT;EE;CM;ES;CV;MZ;RU;GD;AE;ET;BA;NE;ER;GH;VC;KW;GN;GB;RW;FK;GI;GP;MM;TZ;IQ;SL;AG;VG;KM;BS;CU;SC;GQ;MW;BF;ZW;MH;LI;OM;BO;MA;TL;PL;XK;KY;PN;SS;AT;TV' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=OK' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

