from flask import Blueprint, render_template, session, escape

blueprint = Blueprint('home', __name__)

@blueprint.route("/")
def home():
		if 'token' in session:
				return 'Logged in with token %s' % escape(session['token'])
		return 'You are not logged in'
