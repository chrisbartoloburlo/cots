echo "Running petstore SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :8080 |awk 'NR > 1 {print $2}' |xargs kill -15
export JAVA_HOME=`/usr/libexec/java_home -v 1.8` && export DISABLE_OAUTH=1
cd $wd/benchmarks/openapi-petstore-master/
mvn clean package
cd $wd
screen -dm -S settssutscreen bash -c "export JAVA_HOME=`/usr/libexec/java_home -v 1.8` && export DISABLE_OAUTH=1; java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/petstore-benchmarks/results/jacoco.exec -jar $wd/benchmarks/openapi-petstore-master/target/openapi-petstore-3.0.0.jar"

while ! nc -z localhost 8080; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.petstore.Wrapper

screen -S settssutscreen -p 0 -X stuff "^C"
export JAVA_HOME=`/usr/libexec/java_home -v 17`
java -jar jacococli.jar report ./scripts/petstore-benchmarks/results/jacoco.exec --classfiles ./benchmarks/openapi-petstore-master/target/classes --html ./scripts/petstore-benchmarks/results/ --csv ./scripts/petstore-benchmarks/results/settscoverage.csv --name petstore --sourcefiles ./benchmarks/openapi-petstore-master/src/main/java/org/openapitools
echo "Finished petstore SeTTS tests.\n"
sleep 1
