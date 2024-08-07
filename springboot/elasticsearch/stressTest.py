import requests
import time
from concurrent.futures import ThreadPoolExecutor, as_completed

def measureResponseTime(url, keyword, requestCount=10000):
    totalTime = 0
    errorCount = 0

    def calculate(session, url, keyword):
        start_time = time.time()
        response = session.get(url, params={'keyword': keyword})
        end_time = time.time()
        response_time = end_time - start_time
        return response, response_time

    with ThreadPoolExecutor(max_workers=10) as executor:
        with requests.Session() as session:
            futures = [executor.submit(calculate, session, url, keyword) for _ in range(requestCount)]
            for future in as_completed(futures):
                try:
                    response, response_time = future.result()
                    totalTime += response_time
                    if response.status_code != 200:
                        print(f"[Error] : status code ==> {response.status_code}")
                        errorCount +=1
                except Exception as e:
                    print(f"[failed] ==> {e}")
                    errorCount +=1

    average_time = totalTime / requestCount
    error_rate = errorCount / requestCount
    return average_time, error_rate

searchByMySQL = "http://localhost:8080/products/search/mysql"
searchByElasticSearch = "http://localhost:8080/products/search/elasticsearch"
keyword = "test" 

print("-------------------        starting stress test... -------------------")

responseTimeByMySQL, errorRateByMySQL = measureResponseTime(searchByMySQL, keyword)
print(f"MySQL average response time over 10000 requests: {responseTimeByMySQL:.4f} seconds")
print(f"MySQL error rate: {errorRateByMySQL:.2%}")

responseTimeByElastic, errorRateByElastic = measureResponseTime(searchByElasticSearch, keyword)
print(f"Elasticsearch average response time over 10000 requests: {responseTimeByElastic:.4f} seconds")
print(f"Elasticsearch error rate: {errorRateByElastic:.2%}")

print("-------------------   END  -------------------")