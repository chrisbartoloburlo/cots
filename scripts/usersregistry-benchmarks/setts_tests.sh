echo "Running usersregistry SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :8080 |awk 'NR > 1 {print $2}' |xargs kill -15
export JAVA_HOME=`/usr/libexec/java_home -v 17`
screen -dm -S settssutscreen bash -c "mvn -DskipTests=true clean package -f ./benchmarks/usersregistry/pom.xml && java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/usersregistry-benchmarks/results/jacoco.exec -jar $wd/benchmarks/usersregistry/target/api-4.1.0.jar"

while ! nc -z localhost 8080; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.usersregistry.Wrapper

screen -S settssutscreen -p 0 -X stuff "^C"
java -jar jacococli.jar report ./scripts/usersregistry-benchmarks/results/jacoco.exec --classfiles ./benchmarks/usersregistry/target/classes --html ./scripts/usersregistry-benchmarks/results/ --csv ./scripts/usersregistry-benchmarks/results/settscoverage.csv --name usersregistry --sourcefiles ./benchmarks/usersregistry/src/main/java/com/github/throyer/common/springboot/
echo "Finished usersregistry SeTTS tests.\n"
sleep 1
