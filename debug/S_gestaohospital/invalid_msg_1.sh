curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/2' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/431529176' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/' \
  --header 'Content-Type: application/json' \
  --data '{"address":"Test Address","availableBeds":50,"beds":100,"latitude":"-16.1854539314037","longitude":"-105.22265712850185","name":"Test Name"}' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/leitos' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/1429008869/leitos' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request PUT \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f' \
  --header 'Content-Type: application/json' \
  --data '{"address":"Test Address","availableBeds":50,"beds":100,"latitude":"28.596103104890318","longitude":"75.86626592579762","name":"Test Name"}' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/pacientes/checkin' \
  --header 'Content-Type: application/json' \
  --data '{"active":true,"birthDate":"1998-07-22T20:15:45.345875+01:00","gender":"Male","name":"test"}' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/1429008869/pacientes/checkin' \
  --header 'Content-Type: application/json' \
  --data '{"active":true,"birthDate":"1998-07-22T20:15:45.345875+01:00","gender":"Male","name":"test"}' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/pacientes' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/1429008869/pacientes' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/pacientes/6450c932b3d8c177bbcea670' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request PUT \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/pacientes/6450c932b3d8c177bbcea670' \
  --header 'Content-Type: application/json' \
  --data '{"active":true,"birthDate":"1998-07-22T20:15:45.345875+01:00","gender":"Male","name":"test"}' \
  --location \
  --max-redirs 32

curl \
  --request PUT \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/pacientes/655996946' \
  --header 'Content-Type: application/json' \
  --data '{"active":true,"birthDate":"1998-07-22T20:15:45.345875+01:00","gender":"Male","name":"test"}' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/pacientes/checkout' \
  --header 'Content-Type: application/json' \
  --header 'Content-Length: 24' \
  --data '6450c932b3d8c177bbcea670' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/pacientes/checkout' \
  --header 'Content-Type: application/json' \
  --header 'Content-Length: 9' \
  --data '655996946' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/1429008869/estoque' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque' \
  --header 'Content-Type: application/json' \
  --data '{"description":"test product","name":"test name","productName":"test product name","quantity":20}' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/1429008869/estoque' \
  --header 'Content-Type: application/json' \
  --data '{"description":"test product","name":"test name","productName":"test product name","quantity":20}' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque/6450c933b3d8c177bbcea671' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque/-155886662' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request PUT \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque/6450c933b3d8c177bbcea671' \
  --header 'Content-Type: application/json' \
  --data '{"description":"test product","name":"test name","productName":"test product name","quantity":20}' \
  --location \
  --max-redirs 32

curl \
  --request PUT \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque/-155886662' \
  --header 'Content-Type: application/json' \
  --data '{"description":"test product","name":"test name","productName":"test product name","quantity":20}' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/transferencia/6450c933b3d8c177bbcea671' \
  --header 'Content-Type: application/json' \
  --header 'Content-Length: 2' \
  --data '10' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/transferencia/6450c933b3d8c177bbcea671' \
  --header 'Content-Type: application/json' \
  --header 'Content-Length: 2' \
  --data '10' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/1/estoque' \
  --header 'Content-Type: application/json' \
  --data '{"description":"test product","name":"test name","productName":"test product name","quantity":20}' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8080/v1/hospitais/2/estoque' \
  --header 'Content-Type: application/json' \
  --data '{"description":"test product","id":"6450c933b3d8c177bbcea673","name":"test product name","productName":"test product name","quantity":20}' \
  --location \
  --max-redirs 32

curl \
  --request DELETE \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque/6450c933b3d8c177bbcea671' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request DELETE \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/estoque/-155886662' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/hospitaisProximos?raio=100.0' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/1429008869/hospitaisProximos?raio=100.0' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f/proximidades' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/1429008869/proximidades' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/1/proximidades' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request DELETE \
  --url 'http://localhost:8080/v1/hospitais/6450c932b3d8c177bbcea66f' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request DELETE \
  --url 'http://localhost:8080/v1/hospitais/1429008869' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8080/v1/hospitais/maisProximo?lat=36.550000000000004&lon=9.44&raioMaximo=0.0' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

