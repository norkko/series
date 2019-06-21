import redis
from flask import Flask

app = Flask(__name__)
cache = redis.Redis(host='redis', port=6379)

def get_count():
        return cache.incr('hits')

@app.route('/')
def index():
    count = get_count()
    return '{}\n'.format(count)