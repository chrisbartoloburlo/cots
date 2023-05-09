echo "Running usersregistry Manual tests..."

wd=`pwd`

# Start SUT with tests
lsof -i :8080 |awk 'NR > 1 {print $2}' |xargs kill -15
cd benchmarks/usersregistry/ && mvn clean && mvn '-Dtest=com.github.throyer.common.springboot.*Tests,com.github.throyer.common.springboot.controllers.*Tests' -q test jacoco:report
cd ../..

echo "Finished usersregistry Manual tests.\n"
sleep 1
