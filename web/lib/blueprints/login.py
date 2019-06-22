from flask import Blueprint, session, redirect, url_for, request
import requests

blueprint = Blueprint('login', __name__)

@blueprint.route("/login", methods=['GET', 'POST'])
def login():
		if 'token' in session:
				return redirect(url_for('home.home'))
		if request.method == 'POST':
				r = requests.post('http://rest:8081/login', json={"username" : request.form['username'], "password" : request.form['password']})
				session['token'] = r.text
				return redirect(url_for('home.home'))
		return '''
        <form action="" method="post">
            <p>Username<br><input type=text name=username>
            <p>Password<br><input type=text name=password>
            <p>Login</br><input type=submit value=Login>
        </form>
    '''