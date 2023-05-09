curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/all' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/capital/Cayenne' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/capital/TAGIUZXRURJCYXDWCPZT' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=SZ;SR;HK;NA;PM;BH;MA;JP;TD;RS;BL;MO;DO;BV;EE;GA;OM;NP;CH;CK;SN;UA;GD;MH;NR;PR;MZ;GH;KE;MY;AW;NG;IS;MV;AT' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/rest/v2/alpha?codes=IY' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

