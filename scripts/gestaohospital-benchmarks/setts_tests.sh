echo "Running gestaohospital SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :8080 |awk 'NR > 1 {print $2}' |xargs kill -15
screen -dm -S settssutscreen bash -c "export JAVA_HOME=`/usr/libexec/java_home -v 1.8` && mvn -DskipTests=true clean package -f ./benchmarks/gestaohospital/pom.xml && java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/gestaohospital-benchmarks/results/jacoco.exec -jar $wd/benchmarks/gestaohospital/target/gestaohospitalar-0.0.1.jar"

while ! nc -z localhost 8080; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.gestaohospital.Wrapper

screen -S settssutscreen -p 0 -X stuff "^C"
export JAVA_HOME=`/usr/libexec/java_home -v 17`
java -jar jacococli.jar report ./scripts/gestaohospital-benchmarks/results/jacoco.exec --classfiles ./benchmarks/gestaohospital/target/classes --html ./scripts/gestaohospital-benchmarks/results/ --csv ./scripts/gestaohospital-benchmarks/results/settscoverage.csv --name gestaohospital --sourcefiles ./benchmarks/gestaohospital/src/main/java
echo "Finished gestaohospital SeTTS tests.\n"
sleep 1
