from flask import Blueprint, render_template

blueprint = Blueprint('register', __name__)

@blueprint.route("/register")
def register():
    return "register"
