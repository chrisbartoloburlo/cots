echo "Running petclinic SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :9966 |awk 'NR > 1 {print $2}' |xargs kill -15
screen -dm -S settssutscreen bash -c "mvn clean package -f $wd/benchmarks/spring-petclinic-rest/pom.xml && java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/petclinic-benchmarks/results/common/jacoco.exec -jar $wd/benchmarks/spring-petclinic-rest/target/spring-petclinic-rest-2.6.2.jar"

while ! nc -z localhost 9966; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.petclinic.Wrapper

#cd benchmarks/spring-petclinic-rest/ && mvn '-Dtest=org.springframework.samples.petclinic.rest.controller.*ControllerTests' -q test
#cd ../..

screen -S settssutscreen -p 0 -X stuff "^C\n^C\n"
lsof -i :9966 |awk 'NR > 1 {print $2}' |xargs kill -15
java -jar jacococli.jar report ./scripts/petclinic-benchmarks/results/common/jacoco.exec --classfiles ./benchmarks/spring-petclinic-rest/target/classes --html ./scripts/petclinic-benchmarks/results/common/ --csv ./scripts/petclinic-benchmarks/results/common/settscoverage.csv --name petclinic --sourcefiles ./benchmarks/spring-petclinic-rest/src/main/java/org/springframework/samples/petclinic
echo "Finished petclinic SeTTS tests.\n"
sleep 1a
