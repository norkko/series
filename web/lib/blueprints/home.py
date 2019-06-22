from flask import Blueprint, render_template

blueprint = Blueprint('home', __name__)

@blueprint.route("/")
def home():
    return "home"

blueprintt = Blueprint('yeet', __name__)

@blueprintt.route("/")
def yeet():
    return "home"
