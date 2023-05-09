echo "Running petclinic Manual tests..."

wd=`pwd`

# Start SUT with tests
lsof -i :9966 |awk 'NR > 1 {print $2}' |xargs kill -15
cd benchmarks/spring-petclinic-rest/ && mvn clean && mvn '-Dtest=org.springframework.samples.petclinic.rest.controller.*ControllerTests' -q test jacoco:report
cd ../..

echo "Finished petclinic Manual tests.\n"
sleep 1
