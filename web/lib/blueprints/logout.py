from flask import Blueprint, session, url_for, redirect

blueprint = Blueprint('logout', __name__)

@blueprint.route("/logout")
def logout():
		session.pop('token', None)
		return redirect(url_for('home.home'))
