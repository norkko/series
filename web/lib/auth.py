from flask import Blueprint, session, render_template, redirect, escape, url_for, request
from requests.exceptions import Timeout
import requests

blueprint = Blueprint(
	'auth',
	__name__
)

@blueprint.route("/")
def home():
		return render_template('home.html', title = 'Home')

# switch to flaskwtf for forms later

@blueprint.route("/register", methods=['GET', 'POST'])
def register():
		if request.method == 'POST':
				try:
						resp = requests.post(
							'http://rest:8081/register', 
							timeout=1.0,
							json={"username" : request.form['username'], "password" : request.form['password']})

						return redirect(url_for('auth.home'))
				except Timeout as ex:
					print("Exception ", ex)
		return render_template('register.html', title='Register')

@blueprint.route('/login', methods=['GET', 'POST'])
def login():
		if request.method == 'POST':
				try:
						resp = requests.post(
							'http://rest:8081/login',
							timeout=1.0,
							json={"username" : request.form['username'], "password" : request.form['password']})

						session['token'] = r.text
						return redirect(url_for('auth.home'))
				except Timeout as ex:
					print("Exception ", ex)
		return render_template('login.html', title='Login')

@blueprint.route('/logout')
def logout():
		session.pop('token', None)
		return redirect(url_for('auth.home'))