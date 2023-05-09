curl \
  --request GET \
  --url 'http://localhost:50280/v1/images/?format=json&page_size=2&q=test' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:50280/v1/images/5f9ed3ba-b762-459e-9165-094470dc4805/?format=json' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:50280/v1/images/oembed/?format=json&url=https://cdn.stocksnap.io/img-thumbs/960w/91KVXXZQWQ.jpg' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

