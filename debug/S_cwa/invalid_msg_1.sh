curl \
  --request POST \
  --url 'http://localhost:8080/version/v1/tan' \
  --header 'Content-Type: application/json' \
  --header 'cwa-fake: Some(1)' \
  --data '{"registrationToken":"1ea6ce8a-9740-41ea-bb37-0242ac130002","responsePadding":"1"}' \
  --location \
  --max-redirs 32

