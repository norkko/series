from flask import Flask, session, render_template, redirect, escape, url_for, request
import logging
import redis
import requests
import os

app = Flask(__name__)
app.logger.setLevel(logging.DEBUG)
cache = redis.Redis(host='redis', port=6379)

@app.route('/')
def index():
		app.logger.debug('hi')
		if 'token' in session:
				return 'Logged in with %s' % escape(session['token'])
		return 'You are not logged in'

@app.route('/register', methods=['GET', 'POST'])
def register():
		if 'token' in session:
			return redirect(url_for('index'))
		if request.method == 'POST':
				requests.post('http://rest:8081/register',
					json={"username" : request.form['username'], "password" : request.form['password']})
				# handle failed registration
				return redirect(url_for('login'))
		return '''
        <form action="" method="post">
            <p>Username<br><input type=text name=username>
            <p>Password<br><input type=text name=password>
            <p>Register</br><input type=submit value=Register>
        </form>
    '''

@app.route('/login', methods=['GET', 'POST'])
def login():
		if 'token' in session:
			return redirect(url_for('index'))
		if request.method == 'POST':
				r = requests.post('http://rest:8081/login',
					json={"username" : request.form['username'], "password" : request.form['password']})
				session['token'] = r.text
				# handle incorrect login
				app.logger.debug(r)
				return redirect(url_for('index'))
		return '''
        <form action="" method="post">
            <p>Username<br><input type=text name=username>
            <p>Password<br><input type=text name=password>
            <p>Login</br><input type=submit value=Login>
        </form>
    '''

@app.route('/logout')
def logout():
		session.pop('token', None)
		return redirect(url_for('index'))

try:  
   app.secret_key = os.environ["APP_SECRET"]
except KeyError: 
   print("Environment variable APP_SECRET not set.")
   sys.exit(1)

if __name__ == '__main__':
		app.run(debug=True)