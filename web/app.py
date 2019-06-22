from flask import Flask
from lib.blueprints import home, login, logout, register
import logging
import redis

app = Flask(__name__)
app.logger.setLevel(logging.DEBUG)

cache = redis.Redis(host='redis', port=6379)

# Register blueprints
app.register_blueprint(home.blueprint)
app.register_blueprint(login.blueprint)
app.register_blueprint(logout.blueprint)
app.register_blueprint(register.blueprint)

if __name__ == '__main__':
		app.run(debug=True)