# primeNumbersRESTApi
1. The Spring boot project is developed with Java 17.
2. The project is built, test and run using Maven.
3. The service is hosted in a container as a serverless GCP application.( https://primesrestapi-oryc3brmdq-nw.a.run.app/primes/200)
4. application_host_url=https://primesrestapi-oryc3brmdq-nw.a.run.app is made configurable in application.properties to start the test services independently during the maven build.
5. Multiple algorithms such as Eratosthenes,Atkin are supported based on optional parameters, and supports parameter "primes" as default.
https://primesrestapi-oryc3brmdq-nw.a.run.app/primes/200?algorithm=Eratosthenes
https://primesrestapi-oryc3brmdq-nw.a.run.app/primes/200?algorithm=Atkin
https://primesrestapi-oryc3brmdq-nw.a.run.app/primes/200?algorithm=primes
4. Caching has been implemented to improve API response time for all three algorithms.
5. Adding the content types as parameter in the request header, Accept=application/xml or Accept=application/json will return the varying content responses such as XML/JSON accordingly. 

![Capture](https://github.com/SriBaraniVasthan/primeNumbersRESTApi/assets/63550126/dd726415-0368-45fe-95e7-bf9ed12f465b)
![Capture3](https://github.com/SriBaraniVasthan/primeNumbersRESTApi/assets/63550126/b07076d9-fa56-4f77-a617-92c23c8c368b)
![Capture2](https://github.com/SriBaraniVasthan/primeNumbersRESTApi/assets/63550126/4f8c03a9-dd5a-4e4e-9da9-7ec581a44240)
![Capture5](https://github.com/SriBaraniVasthan/primeNumbersRESTApi/assets/63550126/8bc4d221-7fc9-4a3a-ade6-f268b633f554)
![Capture4](https://github.com/SriBaraniVasthan/primeNumbersRESTApi/assets/63550126/603700b9-eb6f-46e0-8cf1-3d6a9e83a923)
