echo "Running restcountries Manual tests..."

wd=`pwd`

# Start SUT with tests
lsof -i :8080 |awk 'NR > 1 {print $2}' |xargs kill -15
cd benchmarks/restcountries/ && mvn clean && mvn '-Dtest=eu.fayder.restcountries.v1.*,eu.fayder.restcountries.v2.*' -q test jacoco:report
cd ../..

echo "Finished restcountries Manual tests.\n"
sleep 1
