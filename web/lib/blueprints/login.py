from flask import Blueprint, render_template

blueprint = Blueprint('login', __name__)

@blueprint.route("/login")
def login():
    return "login"