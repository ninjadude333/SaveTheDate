from datetime import datetime
from elasticsearch import Elasticsearch
es = Elasticsearch(hosts="10.0.0.45")

doc = {
    'name': 'David gidony',
    'numberOfPeople': '3',
    'groupName': 'Close Family',
    'eMail': 'dudug@index.co.il',
    'cellPhone': '0522808442',
    'validated': 'yes',
    'autoRemind': 'yes',
    'comment': 'tesing 123...',
    'timestamp': datetime.now()
}
res = es.index(index="guestsList", doc_type='doc', id=1, body=doc)
print(res['result'])

res = es.get(index="guestsList", doc_type='doc', id=2)
print(res['_source'])

es.indices.refresh(index="guestsList")

res = es.search(index="guestsList", body={"query": {"match_all": {}}})
print("Got %d Hits:" % res['hits']['total'])
for hit in res['hits']['hits']:
    print(hit)