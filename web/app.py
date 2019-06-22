from flask import Flask, Blueprint
import logging
import redis
import os

from lib import auth

app = Flask(
	__name__
)

app.logger.setLevel(logging.DEBUG)
cache = redis.Redis(host='redis', port=6379)

# Register blueprints
app.register_blueprint(auth.blueprint)

# Set session secret
try:
		app.secret_key = os.environ["APP_SECRET"]
except KeyError: 
		print("Set APP_SECRET")
		sys.exit(1)

if __name__ == '__main__':
		app.run(debug = True)
