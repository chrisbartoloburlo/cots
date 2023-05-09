echo "Running petclinic SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :9966 |awk 'NR > 1 {print $2}' |xargs kill -15
screen -dm -S settssutscreen bash -c "mvn -DskipTests=true clean package -f $wd/benchmarks/spring-petclinic-rest/pom.xml && java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/petclinic-benchmarks/results/jacoco.exec -jar $wd/benchmarks/spring-petclinic-rest/target/spring-petclinic-rest-2.6.2.jar"

while ! nc -z localhost 9966; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.petclinic.Wrapper

screen -S settssutscreen -p 0 -X stuff "^C\n^C\n"
java -jar jacococli.jar report ./scripts/petclinic-benchmarks/results/jacoco.exec --classfiles ./benchmarks/spring-petclinic-rest/target/classes --html ./scripts/petclinic-benchmarks/results/ --csv ./scripts/petclinic-benchmarks/results/settscoverage.csv --name petclinic --sourcefiles ./benchmarks/spring-petclinic-rest/src/main/java/org/springframework/samples/petclinic
echo "Finished petclinic SeTTS tests.\n"
sleep 1
