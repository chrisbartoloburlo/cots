curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/lang/' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=NO;WF;MU;MZ;BT;EE;SI;VC;IT;GL;BL;ZW;MD;HU;CY;SV;UM;CX;WS;PS;AX;AT;UZ;HT;MT;SA;XK;VE;SE;GY;CA;KY;GU;CC;MR;ID;CZ;TL;RW;BQ;GG;KP;BZ;FI;CK;NC;PW;MY;MC;PF;AR;SM;NE;LK;CM;RU;LV;SK;JE;OM;JP;BM;CG;QA;IL;LC;CR;SO;TZ;GR;NZ;MA;RS;FR' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=DW' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

