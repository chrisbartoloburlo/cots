curl \
  --request GET \
  --url 'http://localhost:8081/v2/languages' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?text=I+walk+to+the+store+and+I+bought+milk).&language=fr' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?text=I+walk+to+the+store+and+I+bought+milk).&language=fr&dicts=en-US,de' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?text=I+walk+to+the+store+and+I+bought+milk).&language=fr&motherTongue=en-US' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?text=I+walk+to+the+store+and+I+bought+milk).&language=auto&preferredVariants=en-GB' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?text=we+all+eat+the+fish+and+then+made+dessert.&language=auto&disabledRules=EN_A_VS_AN&enabledOnly=true' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?text=I+walk+to+the+store+and+I+bought+milk).&language=auto&disabledRules=EN_A_VS_AN&level=picky' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?text=I+walk+to+the+store+and+I+bought+milk).&language=auto' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?data=%7B%22annotation%22:%5B%0A+%7B%22text%22:+%22A+%22%7D,%0A+%7B%22markup%22:+%22%3Cb%3E%22%7D,%0A+%7B%22text%22:+%22test%22%7D,%0A+%7B%22markup%22:+%22%3C/b%3E%22%7D%0A%5D%7D&language=auto' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?data=we+all+eat+the+fish+and+then+made+dessert.&language=auto' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?language=auto' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request POST \
  --url 'http://localhost:8081/v2/check?language=auto' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

curl \
  --request GET \
  --url 'http://localhost:8081/v2/words?username=uname&apiKey=apikey' \
  --header 'Content-Type: application/json' \
  --location \
  --max-redirs 32

