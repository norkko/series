from flask import Blueprint, render_template

blueprint = Blueprint('logout', __name__)

@blueprint.route("/logout")
def logout():
    return "logout"
