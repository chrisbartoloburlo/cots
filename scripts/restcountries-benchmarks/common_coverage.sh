echo "Running restcountries SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :8080 |awk 'NR > 1 {print $2}' |xargs kill -15
screen -dm -S settssutscreen bash -c "mvn -DskipTests=true clean package -f ./benchmarks/restcountries/pom.xml && java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/restcountries-benchmarks/results/common/jacoco.exec -jar $wd/benchmarks/restcountries/target/restcountries-sut.jar"

while ! nc -z localhost 8080; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.restcountries.Wrapper

cd benchmarks/restcountries/ && mvn '-Dtest=eu.fayder.restcountries.v1.*,eu.fayder.restcountries.v2.*' -q -DskipITs test
cd ../../
screen -S settssutscreen -p 0 -X stuff "^C"
java -jar jacococli.jar report ./scripts/restcountries-benchmarks/results/common/jacoco.exec --classfiles ./benchmarks/restcountries/target/classes --html ./scripts/restcountries-benchmarks/results/common/ --csv ./scripts/restcountries-benchmarks/results/common/bothcoverage.csv --name restcountries --sourcefiles ./benchmarks/restcountries/src/main/java
echo "Finished restcountries SeTTS tests.\n"
sleep 1
