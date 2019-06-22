from flask import Blueprint, render_template, session, redirect, url_for, request
import requests

blueprint = Blueprint('register', __name__)

@blueprint.route("/register", methods=['GET', 'POST'])
def register():
		if 'token' in session:
				return redirect(url_for('home.home'))
		if request.method == 'POST':
				requests.post('http://rest:8081/register', json={"username" : request.form['username'], "password" : request.form['password']})
				return redirect(url_for('home.home'))
		return '''
        <form action="" method="post"> 
            <p>Username<br><input type=text name=username>
            <p>Password<br><input type=text name=password>
            <p>Register</br><input type=submit value=Register>
        </form>
    '''
